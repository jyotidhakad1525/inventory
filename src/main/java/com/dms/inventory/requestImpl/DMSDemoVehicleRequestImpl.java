package com.dms.inventory.requestImpl;

import com.dms.inventory.common.BaseException;
import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.common.ErrorMessages;
import com.dms.inventory.common.Utils;
import com.dms.inventory.entities.DemoTestDriveVehicle;
import com.dms.inventory.entities.DemoTestdriveVehicleAllotment;
import com.dms.inventory.enums.SearchCriteriaType;
import com.dms.inventory.filters.BaseFilter;
import com.dms.inventory.model.*;
import com.dms.inventory.repository.DemoTestDriveVehicleRepository;
import com.dms.inventory.repository.DemoTestdriveVehicleAllotmentRepository;
import com.dms.inventory.specification.CustomSpecification;
import com.dms.inventory.specification.DemoVehicleSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DMSDemoVehicleRequestImpl {

    private final
    DemoTestDriveVehicleRepository demoTestDriveVehicleRepository;
    private final
    DemoTestdriveVehicleAllotmentRepository testdriveVehicleAllotmentRepository;
    private final
    Environment env;
    private final
    RestTemplate restTemplate;

    public DMSDemoVehicleRequestImpl(DemoTestDriveVehicleRepository demoTestDriveVehicleRepository,
                                     DemoTestdriveVehicleAllotmentRepository testdriveVehicleAllotmentRepository,
                                     Environment env, RestTemplate restTemplate) {
        this.demoTestDriveVehicleRepository = demoTestDriveVehicleRepository;
        this.testdriveVehicleAllotmentRepository = testdriveVehicleAllotmentRepository;
        this.env = env;
        this.restTemplate = restTemplate;
    }

    public BaseResponse create(DemoVehicleRequest request) {
        DemoVehicle demoVehicle = request.getVehicle();
        int id = demoVehicle.getId();
        String userId = demoVehicle.getUserId();
        DemoTestDriveVehicle demoTestDriveVehicle = new DemoTestDriveVehicle();
        if (id == 0) {
            BeanUtils.copyProperties(demoVehicle, demoTestDriveVehicle);
            demoTestDriveVehicle.setCreatedBy(userId);
            demoTestDriveVehicle.setCreatedDatetime(new Date());
        }
        DemoTestDriveVehicle vehicle = demoTestDriveVehicleRepository.save(demoTestDriveVehicle);
        BaseResponse successResponse = Utils.SuccessResponse();
        successResponse.setConfirmationId(vehicle.getId() + "");
        return successResponse;
    }

    public BaseResponse update(DemoVehicleRequest request) {
        DemoVehicle demoVehicle = request.getVehicle();
        int id = demoVehicle.getId();
        String userId = demoVehicle.getUserId();
        DemoTestDriveVehicle demoTestDriveVehicle = new DemoTestDriveVehicle();
        if (id == 0) {
            // throw exception
        } else {
            demoTestDriveVehicle = demoTestDriveVehicleRepository.findById(id);
            BeanUtils.copyProperties(demoVehicle, demoTestDriveVehicle, "createdDatetime", "createdBy");
            demoTestDriveVehicle.setModifiedBy(userId);
            demoTestDriveVehicle.setModifiedDatetime(new Date());
        }
        DemoTestDriveVehicle vehicle = demoTestDriveVehicleRepository.save(demoTestDriveVehicle);
        BaseResponse successResponse = Utils.SuccessResponse();
        successResponse.setConfirmationId(vehicle.getId() + "");
        return successResponse;
    }

    public DemoVehicleResponse search(BaseFilter request) {
        DemoVehicleResponse response = new DemoVehicleResponse();
        System.out.println("IMPL Request "+request);
        BigInteger orgId = request.getOrgId();
        System.out.println("<<<<<<<<<<<<<<<<<<<orgId>>>>>>>>>>>>>>>>>"+orgId);
        BigInteger branch = request.getBranch();
        System.out.println("<<<<<<<<<<<<<<<<<<<branch>>>>>>>>>>>>>>>>>"+branch);
        String type = Utils.isEmpty(request.getType()) ? null : request.getType().name();
        System.out.println("<<<<<<<<<<<<<<<<<<<type>>>>>>>>>>>>>>>>>"+type);
        String status = Utils.isEmpty(request.getStatus()) ? null : request.getStatus().name();
        Integer id = request.getId();
        List<DemoTestDriveVehicle> demoTestDriveVehicles = null;
        Specification<DemoTestDriveVehicle> specification = null;
        if (Utils.isNotEmpty(orgId)) {
            specification = Specification.where(DemoVehicleSpecification.hasOrgId(orgId));
        }
        if (Utils.isNotEmpty(branch)) {
            if (Utils.isEmpty(specification)) {
                specification = Specification.where(DemoVehicleSpecification.hasBranch(branch));
            } else {
                specification = specification.and(DemoVehicleSpecification.hasBranch(branch));
            }
        }
        if (Utils.isNotEmpty(status)) {
            if (Utils.isEmpty(specification)) {
                specification = Specification.where(DemoVehicleSpecification.isStatus(status));
            } else {
                specification = specification.and(DemoVehicleSpecification.isStatus(status));
            }
        }
        if (Utils.isNotEmpty(type)) {
            List<String> types = new ArrayList<>();
            types.add(type);
            if (type.toUpperCase().equals("EVENT") || type.toUpperCase().equals("TESTDRIVE")) {
                types.add("BOTH");
                System.out.println("<<<<<<<<<<<<<<<<<<<In IF AFTER BOTH>>>>>>>>>>>>>>>>>"+type);
            }
            if (Utils.isEmpty(specification)) {
                specification = Specification.where(DemoVehicleSpecification.hasType(types));
            } else {
                specification = specification.and(DemoVehicleSpecification.hasType(types));
            }
        }
        if (Utils.isNotEmpty(id)) {
            if (Utils.isEmpty(specification)) {
                specification = Specification.where(DemoVehicleSpecification.hasId(id));
            } else {
                specification = specification.and(DemoVehicleSpecification.hasId(id));
            }
        }
        if (Utils.isEmpty(specification)) {
            // throw exception
        }
        
        Sort sort;
        if (Utils.isNotEmpty(id)) {
            sort = Sort.by("createdDatetime").descending();
            }else {
            	
            	sort = Sort.by("createdDatetime").descending();
            	System.out.println("<<<<<<<<<<<<<<<<<<<IN Condition Else>>>>>>>>>>>>>>>>>");
            }
        long total = 0;
        if (Utils.isNotEmpty(request.getOffset())) {
            int perPage = Utils.isNotEmpty(request.getLimit()) ? request.getLimit() : 50;
            Pageable pageable = PageRequest.of(request.getOffset(), perPage, sort);
            Page<DemoTestDriveVehicle> page = demoTestDriveVehicleRepository.findAll(specification, pageable);
            demoTestDriveVehicles = page.getContent();
            response.setTotalCount(Integer.valueOf(page.getTotalElements() + ""));
            total = page.getTotalElements();
        } else {
        	System.out.println("<<<<<<<<<<<<<<<<<<<IN Condition Else 11>>>>>>>>>>>>>>>>>"+specification);
            demoTestDriveVehicles = demoTestDriveVehicleRepository.findAll(specification, sort);
            System.out.println("<<<<<<<<<<<<<<<<<<<IN Condition Else 22>>>>>>>>>>>>>>>>>"+specification);
        }

        if (Utils.isEmpty(demoTestDriveVehicles)) {
            throw new BaseException(ErrorMessages.DATA_NOT_FOUND);
        }
        Utils.constructSuccessResponse(response);
        response.setCount(demoTestDriveVehicles.size());
        if (response.getCount() > 0 && response.getTotalCount() == 0) {
            response.setTotalCount(Integer.valueOf(total + ""));
        }
        // demoTestDriveVehicles = demoTestDriveVehicleRepository.findAll(specification
        // );
        if (Utils.isEmpty(demoTestDriveVehicles)) {
            throw new BaseException(ErrorMessages.DATA_NOT_FOUND);
        }
        List<DemoVehicle> demoVehicles = Utils.copyListtoList(demoTestDriveVehicles, DemoVehicle.class);
        Map<Integer, VehicleDetails> map = allVehiclesInfo(orgId);
        System.out.println("<<<<<<<<<<<<<<<VehicleDetails>>>>>>>>>>>>>>"+map);
        mapVehicleInfo(demoVehicles, map);
        response.setVehicles(demoVehicles);
        response.setCount(demoVehicles.size());
        if (response.getTotalCount() == 0) {
            response.setTotalCount(response.getCount());
        }
        return response;
    }

    public Map<Integer, VehicleDetails> allVehiclesInfo(BigInteger orgId) {
        //String baseUrl = env.getProperty("dms.vehicle.management.baseurl");
    	System.out.println("<<<<<<<<<<<ORG_ID_IN MAP>>>>>>>>>>>"+orgId);
        String getAllVehiclesUrl = env.getProperty("dms.vehicle.management.getallvehicles");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getAllVehiclesUrl)
                .queryParam("organizationId", orgId);
        System.out.println("<<<<<<<<<<<builder.toUriString()>>>>>>>>>>>>>"+builder.toUriString());
        ResponseEntity<VehicleDetails[]> responseEntity = restTemplate.getForEntity(builder.toUriString(),
                VehicleDetails[].class);
        System.out.println("<<<<<<<<<<<responseEntity.getBody()>>>>>>>>>>>>>"+responseEntity.getBody());
        VehicleDetails[] vehicleDetails = responseEntity.getBody();
        System.out.println("<<<<<<<<<<<vehicleDetails>>>>>>>>>>>>>"+vehicleDetails);
        Map<Integer, VehicleDetails> map = new HashMap<>();
        for (VehicleDetails details : vehicleDetails) {
            map.put(details.getVehicleId(), details);
        }
        return map;
    }

    private void mapVehicleInfo(List<DemoVehicle> demoVehicles, Map<Integer, VehicleDetails> map) {
        for (DemoVehicle demoVehicle : demoVehicles) {
            BigInteger varientId = demoVehicle.getVarientId();
            int vehicleId = demoVehicle.getVehicleId().intValue();
            BigInteger colorId = demoVehicle.getColorId();
            VehicleDetails vehicleDetails = map.get(vehicleId);
            AdditionalVehicleInfo vehicleInfo = new AdditionalVehicleInfo();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<Before varients>>>>>>>>>>>");
            Set<VehicleVariant> varients = vehicleDetails.getVarients();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<varients>>>>>>>>>>>"+varients);
            BeanUtils.copyProperties(vehicleDetails, vehicleInfo);
            for (VehicleVariant varient : varients) {
                Integer id = varient.getId();
                if (id.equals(varientId.intValue())) {
                    BeanUtils.copyProperties(varient, vehicleInfo);
                    vehicleInfo.setVarientId(varient.getId());
                    vehicleInfo.setVarientName(varient.getName());
                    Set<VehicleImage> vehicleImages = varient.getVehicleImages();
                    for (VehicleImage image : vehicleImages) {
                        String color_body = image.getColor();
                        Integer vehicleImageId = image.getVehicleImageId();
                        if (vehicleImageId.equals(colorId.intValue())) {
                            vehicleInfo.setColorId(vehicleImageId);
                            vehicleInfo.setColor(color_body);
                            break;
                        }
                    }
                    break;
                }
            }
            demoVehicle.setVehicleInfo(vehicleInfo);
        }
    }

    public VehicleAllotmentResponse vehicleAllotments(AllotmentSearch request) {
        VehicleAllotmentResponse response = new VehicleAllotmentResponse();
        List<DemoTestdriveVehicleAllotment> vehicleAllotments = testdriveVehicleAllotmentRepository
                .findByDemoVehicleId(request.getId()); // findByVehicleIdAndDate(request.getId(), allotmentDate);
        if (Utils.isEmpty(vehicleAllotments)) {
            throw new BaseException(ErrorMessages.VEHICLE_BOOKING_AVAILABLE, ErrorMessages.SUCCESS.name(),
                    request.getAllotmentDate(), request.getId() + "");
        }
        String allotmentDate = request.getAllotmentDate();
        String format = "dd-MM-yyyy";
        vehicleAllotments = vehicleAllotments.stream().filter(
                a -> Utils.formatDateToString(format, a.getPlannedEndDatetime()).equalsIgnoreCase(allotmentDate))
                .collect(Collectors.toList());
        if (Utils.isEmpty(vehicleAllotments)) {
            throw new BaseException(ErrorMessages.VEHICLE_BOOKING_AVAILABLE, ErrorMessages.SUCCESS.name(),
                    request.getId() + "", request.getAllotmentDate());
        }
        response.setVehicleAllotments(vehicleAllotments);
        Utils.constructSuccessResponse(response);
        response.setCount(vehicleAllotments.size());
        return response;
    }

    public VehicleAllotmentResponse vehicleAllotment(AllotmentSearch request) {
        VehicleAllotmentResponse response = new VehicleAllotmentResponse();
        DemoTestdriveVehicleAllotment allotment = testdriveVehicleAllotmentRepository
                .findById(request.getId().intValue());
        if (Utils.isEmpty(allotment)) {
            throw new BaseException(ErrorMessages.DATA_NOT_FOUND);
        }
        response.setAllotment(allotment);
        Utils.constructSuccessResponse(response);
        return response;
    }

    public BaseResponse vehicleAllotmentCreate(VehicleAllotmentRequest request) {
        DemoTestdriveVehicleAllotment allotment = request.getAllotment();
        allotment.setCreatedDatetime(new Date());
        Utils.ObjectToJson(allotment);
        DemoTestdriveVehicleAllotment vehicleAllotment = testdriveVehicleAllotmentRepository.save(allotment);
        BaseResponse response = Utils.SuccessResponse();
        Utils.ObjectToJson(vehicleAllotment);
        response.setConfirmationId(vehicleAllotment.getId() + "");
        return response;
    }

    public BaseResponse vehicleAllotmentUpdate(VehicleAllotmentRequest request) {
        DemoTestdriveVehicleAllotment allotment = request.getAllotment();
        allotment.setModifiedDatetime(new Date());
        testdriveVehicleAllotmentRepository.save(allotment);
        return Utils.SuccessResponse();
    }

    @SuppressWarnings("null")
    public VehicleAllotmentResponse vehicleAllotmentBookings(VehicleAllotmentRequest request) {
        VehicleAllotmentResponse response = new VehicleAllotmentResponse();
        List<DemoTestdriveVehicleAllotment> vehicleAllotments = null;
        Specification<DemoTestdriveVehicleAllotment> specification = null;
        DemoTestdriveVehicleAllotment allotment = request.getAllotment();
        if (Utils.isNotEmpty(allotment.getEventType())) {
            if (Utils.isEmpty(specification)) {
                specification = Specification
                        .where(CustomSpecification.attribute("eventType", allotment.getEventType()));
            } else {
                specification = specification.and(CustomSpecification.attribute("eventType", allotment.getEventType()));
            }
        }
        if (Utils.isNotEmpty(allotment.getDemoVehicleId())) {
            if (Utils.isEmpty(specification)) {
                specification = Specification
                        .where(CustomSpecification.attribute("demoVehicleId", allotment.getDemoVehicleId()));
            } else {
                specification = specification
                        .and(CustomSpecification.attribute("demoVehicleId", allotment.getDemoVehicleId()));
            }
        }
        if (Utils.isNotEmpty(allotment.getPlannedStartDatetime())
                && Utils.isNotEmpty(allotment.getPlannedEndDatetime())) {
            Specification<DemoTestdriveVehicleAllotment> startDate = CustomSpecification.attribute(
                    "plannedStartDatetime", allotment.getPlannedStartDatetime(),
                    SearchCriteriaType.GREATERTHAN_OR_EQUALTO.name());
            Specification<DemoTestdriveVehicleAllotment> endDateBetween = CustomSpecification.between(
                    "plannedEndDatetime", allotment.getPlannedStartDatetime(), allotment.getPlannedEndDatetime());
            specification = specification.and(startDate.or(endDateBetween));
        } else if (Utils.isNotEmpty(allotment.getPlannedStartDatetime())) {
            specification = specification.and(CustomSpecification.attribute("plannedStartDatetime",
                    allotment.getPlannedStartDatetime(), SearchCriteriaType.GREATERTHAN_OR_EQUALTO.name()));
        } else if (Utils.isNotEmpty(allotment.getPlannedEndDatetime())) {
            specification = specification.and(CustomSpecification.between("plannedEndDatetime",
                    allotment.getPlannedStartDatetime(), allotment.getPlannedEndDatetime()));
        }
        vehicleAllotments = testdriveVehicleAllotmentRepository.findAll(specification);
        if (Utils.isEmpty(vehicleAllotments)) {
            throw new BaseException(ErrorMessages.VEHICLE_BOOKING_AVAILABLE, ErrorMessages.SUCCESS.name(),
                    allotment.getDemoVehicleId() + "", "");
        }
        response.setVehicleAllotments(vehicleAllotments);
        Utils.constructSuccessResponse(response);
        response.setCount(vehicleAllotments.size());
        return response;
    }

    public BaseResponse deleteDemovehicles(int id) {
        DemoTestDriveVehicle driveVehicle = demoTestDriveVehicleRepository.findById(id);
        if (Utils.isEmpty(driveVehicle)) {
            throw new BaseException(ErrorMessages.ALREADY_VEHICLE_DELETED);
        }
        demoTestDriveVehicleRepository.deleteById(id);
        BaseResponse successResponse = Utils.SuccessResponse("Demo Vehicle " + id + " Deleted Successfully");
        return successResponse;
    }
}
