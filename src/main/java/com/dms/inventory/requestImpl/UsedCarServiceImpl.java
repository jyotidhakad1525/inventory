package com.dms.inventory.requestImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dms.inventory.aws.utils.AWSS3Service;
import com.dms.inventory.entities.InventoryUsedCar;
import com.dms.inventory.entities.VehicleSoldInfo;
import com.dms.inventory.exception.RecordNotFoundException;
import com.dms.inventory.interfaces.BuyusedCarNService;
import com.dms.inventory.mapper.InventoryUsedCarMapper;
import com.dms.inventory.model.InventoryUsedCarDto;
import com.dms.inventory.model.SearchResponseInventoryUsedCar;
import com.dms.inventory.model.VehicleSoldInforDto;
import com.dms.inventory.repository.BuyUsedCarServiceRepository;
import com.dms.inventory.repository.VehicleSoldCarServiceRepository;

@Service
public class UsedCarServiceImpl implements BuyusedCarNService {

	final InventoryUsedCarMapper modelMapper;
	private final BuyUsedCarServiceRepository repo;
	private final VehicleSoldCarServiceRepository repo2;

	private final AWSS3Service awss3Service;
	@Value("${document-path}")
	private String path;

	public UsedCarServiceImpl(BuyUsedCarServiceRepository repo, AWSS3Service awss3Service,
			InventoryUsedCarMapper modelMapper,VehicleSoldCarServiceRepository repo2) {
		this.repo = repo;
		this.repo2=repo2;
		this.awss3Service = awss3Service;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<SearchResponseInventoryUsedCar> getByDealerCode(String dealerCode, String make, String model, BigDecimal price1,
			BigDecimal price2, Integer offset, Integer limit) {
		Page<InventoryUsedCar> inv = null;
		List<InventoryUsedCar> resp = new ArrayList<InventoryUsedCar>();
		InventoryUsedCar obj = new InventoryUsedCar();	
		if (Objects.nonNull(dealerCode)) {
            obj.setDealerCode(dealerCode);
        }
		if (Objects.nonNull(make)) {
            obj.setMake(make);
        }
		if (Objects.nonNull(model)) {
            obj.setModel(model);
        }
		Pageable paging = null;
		try {
			if (!Objects.nonNull(offset) && !Objects.nonNull(limit)) {
				paging = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
			} else {
				paging = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "id"));
			}
			inv = repo.findAll(Example.of(obj), paging);
			if (price1 != null && price2 != null) {
				for (InventoryUsedCar inven : inv) {
					if (inven.getVehiclePurchasePrice().compareTo(price1) == 1 || inven.getVehiclePurchasePrice().compareTo(price1) == 0
							|| inven.getVehiclePurchasePrice().compareTo(price2) == -1 || inven.getVehiclePurchasePrice().compareTo(price2) == 0) {
						resp.add(inven);
					}
				}
			} else {
				resp.addAll(inv.getContent());
			}
		} catch (Exception e) {
		}
		List<SearchResponseInventoryUsedCar> sriuc=
		resp.stream().map(i->new SearchResponseInventoryUsedCar(i,repo2.findByInventoryUsedCarId(i.getId()))).collect(Collectors.toList());
		if (Objects.nonNull(sriuc)) {
			Page<SearchResponseInventoryUsedCar> inv1 = new PageImpl<SearchResponseInventoryUsedCar>(sriuc, paging, inv.getTotalElements());
			return inv1;
		}
		return null;
	}

	private InventoryUsedCarDto convertToDto(InventoryUsedCar inventoryUsedCar) {
		InventoryUsedCarDto inventoryUsedCarDto = modelMapper.mapEntityToModel(inventoryUsedCar);
		if(inventoryUsedCarDto.getExtraField()!=null) {
			String[] parts = inventoryUsedCarDto.getExtraField().split("\\|");
			if (parts[0] != "") {
				inventoryUsedCarDto.setDocumentList((Arrays.asList(parts)));
			}
		}
		if (Objects.nonNull(inventoryUsedCar.getMakingYear())) {
			inventoryUsedCarDto.setMakingYear(inventoryUsedCar.getMakingYear().substring(0, 4));
		}
		return inventoryUsedCarDto;
	}
	
	
	@Override
	public InventoryUsedCarDto createInventory(InventoryUsedCarDto inventory) throws ParseException {
		Optional<InventoryUsedCar> entity = repo.findByChassisNumberOrganizationId(inventory.getChassisNumber(),inventory.getOrganizationId());
		String ex = "";
		try {
			if (entity.isPresent()) {
				System.out.println("Duplicate Record exist");
				return null;
			} else {
				InventoryUsedCar inventoryEntity = modelMapper.mapModelToEntity(inventory);
				inventoryEntity.setCreatedDatetime(new Date(System.currentTimeMillis()));
				int size = inventory.getImagefiles().length;
				List<String> srk = new ArrayList<>();
				if(inventory.getImagefiles()!=null) {
					for (int i = 0; i < size; i++) {
						ex = inventory.getImagefiles()[i].getOriginalFilename();
						System.out.println(ex);
						int index = ex.lastIndexOf('.');
						if (index > 0) {
							ex = ex.substring(index + 1);
						}
						System.out.println(ex);
						srk.add(ex);
					}
					String[] strings = srk.toArray(String[]::new);
					for (int i = 0; i < size; i++) {

						if (inventory.getImagefiles()[i].getInputStream().available() > 0) {
							String fileName = awss3Service.uploadFile(inventory.getImagefiles()[i],
									strings[i], inventory.getRcNumber());
							if (Objects.nonNull(inventoryEntity.getImages())) {
								inventoryEntity.setImages(inventoryEntity.getImages() + "|" + path + "UsedVehicles/"
										+ fileName + inventory.getImagefiles()[i].getOriginalFilename());
							} else {
								inventoryEntity.setImages(path + "UsedVehicles/" + fileName
										+ inventory.getImagefiles()[i].getOriginalFilename());
							}
						}
					}	
				}
				
				InventoryUsedCar inventoryEntity1 = repo.save(inventoryEntity);
				return convertToDto(inventoryEntity1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VehicleSoldInfo createVehcileSoldInfo(VehicleSoldInforDto vehicleSoldInforDto) {
		Optional<InventoryUsedCar> entity = repo.findById(vehicleSoldInforDto.getInventoryUsedCarId());
		try {
			VehicleSoldInfo vehicleSoldInfo = new VehicleSoldInfo();
			vehicleSoldInfo.setInventoryUsedCarId(vehicleSoldInforDto.getInventoryUsedCarId());
			vehicleSoldInfo.setCustomerName(vehicleSoldInforDto.getCustomerName());
			vehicleSoldInfo.setCustomerMobileNumber(vehicleSoldInforDto.getCustomerMobileNumber());
			vehicleSoldInfo.setPanCardNumber(vehicleSoldInforDto.getPanCardNumber());
			vehicleSoldInfo.setGstNumber(vehicleSoldInfo.getGstNumber());
			vehicleSoldInfo.setPincode(vehicleSoldInfo.getPincode());
			vehicleSoldInfo.setHouseNo(vehicleSoldInfo.getHouseNo());
			vehicleSoldInfo.setVillageOrTown(vehicleSoldInforDto.getVillageOrTown());
			vehicleSoldInfo.setDistrict(vehicleSoldInforDto.getDistrict());
			vehicleSoldInfo.setIsRural(vehicleSoldInforDto.getIsRural());
			vehicleSoldInfo.setIsUrban(vehicleSoldInforDto.getIsUrban());
			vehicleSoldInfo.setStreet(vehicleSoldInforDto.getStreet());
			vehicleSoldInfo.setCity(vehicleSoldInforDto.getCity());
			vehicleSoldInfo.setState(vehicleSoldInforDto.getState());
			vehicleSoldInfo.setVehicleSellingPrice(vehicleSoldInforDto.getVehicleSellingPrice());
			vehicleSoldInfo.setSalesConsultantName(vehicleSoldInforDto.getSalesConsultantName());
			vehicleSoldInfo.setSellingDate(vehicleSoldInforDto.getSellingDate());
			vehicleSoldInfo.setCreatedDatetime(new Date(System.currentTimeMillis()));

			if (vehicleSoldInforDto.getPanCardDocFile() != null) {
				String ex1 = vehicleSoldInforDto.getPanCardDocFile().getOriginalFilename();
				int index1 = ex1.lastIndexOf('.');
				if (index1 > 0) {
					ex1 = ex1.substring(index1 + 1);
				}
				if (vehicleSoldInforDto.getPanCardDocFile().getInputStream().available() > 0) {
					String fileName = awss3Service.uploadFile(vehicleSoldInforDto.getPanCardDocFile(), ex1,
							entity.get().getRcNumber());
					if (Objects.nonNull(vehicleSoldInforDto.getPanCardDocFile())) {
						vehicleSoldInfo.setPanCardDocFile(
								vehicleSoldInforDto.getPanCardDocFile() + "|" + path + "SoldVehicles/" + fileName
										+ vehicleSoldInforDto.getPanCardDocFile().getOriginalFilename());

					} else {
						vehicleSoldInfo.setPanCardDocFile(path + "SoldVehicles/" + fileName
								+ vehicleSoldInforDto.getPanCardDocFile().getOriginalFilename());
					}
				}
			}
			
			if (vehicleSoldInforDto.getAddressProofFile()!=null) {
				String ex2 = vehicleSoldInforDto.getAddressProofFile().getOriginalFilename();
				int index2 = ex2.lastIndexOf('.');
				if (index2 > 0) {
					ex2 = ex2.substring(index2 + 1);
				}
				if (vehicleSoldInforDto.getAddressProofFile().getInputStream().available() > 0) {
					String fileName = awss3Service.uploadFile(vehicleSoldInforDto.getAddressProofFile(), ex2,
							entity.get().getRcNumber());
					if (Objects.nonNull(vehicleSoldInforDto.getAddressProofFile())) {
						vehicleSoldInfo.setAddressProofDocFile(vehicleSoldInforDto.getAddressProofFile() + "|" + path + "SoldVehicles/" + fileName
								+ vehicleSoldInforDto.getAddressProofFile().getOriginalFilename());
					} else {
						vehicleSoldInfo.setAddressProofDocFile(path + "SoldVehicles/" + fileName
								+ vehicleSoldInforDto.getAddressProofFile().getOriginalFilename());
					}
				}
			}		
			return (repo2.save(vehicleSoldInfo));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public InventoryUsedCarDto updateInventory(InventoryUsedCarDto inventory) {
		Optional<InventoryUsedCar> entity = repo.findById(inventory.getId());
		String ex = "";
		if (!entity.isPresent())
			return null;
		try {
			InventoryUsedCar inventoryUsedCar = entity.get();
			InventoryUsedCar inventoryEntity = modelMapper.mapModelToEntity(inventory);
			if (inventoryEntity.getOrganizationId() != null) {
				inventoryUsedCar.setOrganizationId(inventoryEntity.getOrganizationId());
			}
			if (inventoryEntity.getLocation()!= null) {
				inventoryUsedCar.setLocation(inventoryEntity.getLocation());
			}
			if (inventoryEntity.getDealerCode()!= null) {
				inventoryUsedCar.setDealerCode(inventoryEntity.getDealerCode());
			}
			if (inventoryEntity.getMake()!= null) {
				inventoryUsedCar.setMake(inventoryEntity.getMake());
			}
			if (inventoryEntity.getModel()!= null) {
				inventoryUsedCar.setModel(inventoryEntity.getModel());
			}
			if (inventoryEntity.getVariant()!= null) {
				inventoryUsedCar.setVariant(inventoryEntity.getVariant());
			}
			if (inventoryEntity.getColor()!= null) {
				inventoryUsedCar.setColor(inventoryEntity.getColor());
			}
			if (inventoryEntity.getFuel()!= null) {
				inventoryUsedCar.setFuel(inventoryEntity.getFuel());
			}
			if (inventoryEntity.getTransmission()!= null) {
				inventoryUsedCar.setTransmission(inventoryEntity.getTransmission());
			}
			if (inventoryEntity.getMakingMonth()!= null) {
				inventoryUsedCar.setMakingMonth(inventoryEntity.getMakingMonth());
			}
			if (inventoryEntity.getMakingYear()!= null) {
				inventoryUsedCar.setMakingYear(inventoryEntity.getMakingYear());
			}
			if (inventoryEntity.getRcNumber() != null) {
				inventoryUsedCar.setRcNumber(inventoryEntity.getRcNumber());
			}
			if (inventoryEntity.getRegistrationDate() != null) {
				inventoryUsedCar.setRegistrationDate(inventoryEntity.getRegistrationDate());
			}
			if (inventoryEntity.getRegistrationValidUpto() != null) {
				inventoryUsedCar.setRegistrationValidUpto(inventoryEntity.getRegistrationValidUpto());
			}
			if (inventoryEntity.getVinNumber() != null) {
				inventoryUsedCar.setVinNumber(inventoryEntity.getVinNumber());
			}
			if (inventoryEntity.getEngineNumber() != null) {
				inventoryUsedCar.setEngineNumber(inventoryEntity.getEngineNumber());
			}
			if (inventoryEntity.getChassisNumber() != null) {
				inventoryUsedCar.setChassisNumber(inventoryEntity.getChassisNumber());
			}
			if (inventoryEntity.getNoOfOwners()!= null) {
				inventoryUsedCar.setNoOfOwners(inventoryEntity.getNoOfOwners());
			}
			if (inventoryEntity.getVehiclePurchaseDate()!= null) {
				inventoryUsedCar.setVehiclePurchaseDate(inventoryEntity.getVehiclePurchaseDate());
			}
			if (inventoryEntity.getVehiclePurchasePrice()!= null) {
				inventoryUsedCar.setVehiclePurchasePrice(inventoryEntity.getVehiclePurchasePrice());
			}
			if (inventoryEntity.getInsuranceType() != null) {
				inventoryUsedCar.setInsuranceType(inventoryEntity.getInsuranceType());
			}
			if (inventoryEntity.getInsuranceValidUpto()!= null) {
				inventoryUsedCar.setInsuranceValidUpto(inventoryEntity.getInsuranceValidUpto());
			}
			if (inventoryEntity.getDrivenKms()!= null) {
				inventoryUsedCar.setDrivenKms(inventoryEntity.getDrivenKms());
			}
			if (inventoryEntity.getVehicleSellingPrice()!= null) {
				inventoryUsedCar.setVehicleSellingPrice(inventoryEntity.getVehicleSellingPrice());
			}
			if (inventoryEntity.getEvalutorName()!= null) {
				inventoryUsedCar.setEvalutorName(inventoryEntity.getEvalutorName());
			}
			inventoryUsedCar.setModifiedBy(inventory.getModifiedBy());
			inventoryUsedCar.setModifiedDate(new Date(System.currentTimeMillis()));
			
			if(inventory.getImagefiles()!=null) {
				int size = inventory.getImagefiles().length;
				List<String> srk = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					ex = inventory.getImagefiles()[i].getOriginalFilename();
					System.out.println(ex);
					int index = ex.lastIndexOf('.');
					if (index > 0) {
						ex = ex.substring(index + 1);
					}
					System.out.println(ex);
					srk.add(ex);
				}
				String[] strings = srk.toArray(String[]::new);
				for (int i = 0; i < size; i++) {

					if (inventory.getImagefiles()[i].getInputStream().available() > 0) {
						String fileName = awss3Service.uploadFile(inventory.getImagefiles()[i],
								strings[i], inventory.getRcNumber());
						if (Objects.nonNull(inventoryEntity.getImages())) {
							inventoryUsedCar.setImages(inventoryEntity.getImages() + "|" + path + "UsedVehicles/"
									+ fileName + inventory.getImagefiles()[i].getOriginalFilename());
						} else {
							inventoryUsedCar.setImages(path + "UsedVehicles/" + fileName
									+ inventory.getImagefiles()[i].getOriginalFilename());
						}
					}
				}
			}
			InventoryUsedCar inventoryEntity1 = repo.save(inventoryUsedCar);
			return convertToDto(inventoryEntity1);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean deleteDocument(Integer id) {
		Optional<InventoryUsedCar> customer = repo.findById(id);
		if (customer.isPresent()) {
			InventoryUsedCar inventoryUsedCar = customer.get();
			String key = inventoryUsedCar.getRcNumber().concat("/");
			convertToDto(inventoryUsedCar).getDocumentList().forEach(file -> {
				String file1 = file.substring(file.lastIndexOf("/") + 1);
				boolean result = awss3Service.deleteFile(key.concat(file1));
			});
			repo.deleteById(id);
			return true;
		} else {
			throw new RecordNotFoundException(id + "is not valid");
		}
	}

	@Override
	public InventoryUsedCarDto getByCarId(long id) {
		Optional<InventoryUsedCar> inventoryUsedCar = repo.findById((int) id);
		if (inventoryUsedCar.isPresent()) {
			return convertToDto(inventoryUsedCar.get());
		} else {
			return null;
		}
	}
}
