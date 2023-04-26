package com.dms.inventory.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dms.inventory.entities.VehicleStockInventoryEntity;
import com.dms.inventory.mapper.VehicleStockInventoryMapper;
import com.dms.inventory.model.AddVehicleInventoryRequest;
import com.dms.inventory.model.SearchVehicleInventoryRequest;
import com.dms.inventory.model.SearchVehicleInventoryResponse;
import com.dms.inventory.repository.VehicleStockInventoryRepo;

@Service
public class SearchVehicleInventoryServiceImpl implements SearchVehicleInventoryService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private VehicleStockInventoryMapper mapper;

	@Autowired
	private VehicleStockInventoryRepo repo;

	@Override
	public SearchVehicleInventoryResponse searchVehicleInventorySource(
			SearchVehicleInventoryRequest searchVehicleInventoryRequest) {
		// TODO : include purchase from and to dates from PPT
		SearchVehicleInventoryResponse response = new SearchVehicleInventoryResponse();
		StringBuilder query = new StringBuilder();
		List<VehicleStockInventoryEntity> queryResults;
		List<AddVehicleInventoryRequest> modelList = new ArrayList<>();
		boolean andConditionAdded = false;

		query.append("select  * from vehicle_stock_inventory WHERE");

		if (null != searchVehicleInventoryRequest.getPurchaseFromDate() 
				&& null != searchVehicleInventoryRequest.getPurchaseToDate()) {
			query.append(this.getAndCondition(andConditionAdded)).append(
					" purchase_date  between CAST(:purchaseFromDate as date) and CAST(:purchaseToDate as date) ");
			andConditionAdded = true;
		}

		if (null != searchVehicleInventoryRequest.getVinNum() && !searchVehicleInventoryRequest.getVinNum().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append(" vin_num = (:vin_num)");
			andConditionAdded = true;
		}
		
		if (null != searchVehicleInventoryRequest.getEngineNum() && !searchVehicleInventoryRequest.getEngineNum().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append("  engine_num = (:engine_num)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getPurchaseInvoiceNum() && !searchVehicleInventoryRequest.getPurchaseInvoiceNum().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded))
					.append("  purchase_invoice_num = (:purchase_invoice_num)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getPurchasedFrom() && !searchVehicleInventoryRequest.getPurchasedFrom().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append("  purchased_from = (:purchased_from)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getLocation() &&  !searchVehicleInventoryRequest.getLocation().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append("  location = (:location)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getDealerCode() && !searchVehicleInventoryRequest.getDealerCode().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append("  dealer_code = (:dealer_code)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getVehicleFinancedBy() && !searchVehicleInventoryRequest.getVehicleFinancedBy().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded))
					.append("	 vehicle_financed_by = (:vehicle_financed_by)");
			andConditionAdded = true;
		}
		if (null != searchVehicleInventoryRequest.getStatus() && !searchVehicleInventoryRequest.getStatus().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append(" 	 status = (:status)");
			andConditionAdded = true;
		}
		
		if (null != searchVehicleInventoryRequest.getOrgId() && !searchVehicleInventoryRequest.getOrgId().isEmpty()) {
			query.append(this.getAndCondition(andConditionAdded)).append(" org_id = (:org_id)");
			
		}
		
		if(null != searchVehicleInventoryRequest.getLimit() &&  !searchVehicleInventoryRequest.getLimit().isEmpty() && 
				null != searchVehicleInventoryRequest.getOffset() && !searchVehicleInventoryRequest.getOffset().isEmpty()) {
			query.append(" limit :limit offset :offset");
			
		}
		Query q = entityManager.createNativeQuery(query.toString(), VehicleStockInventoryEntity.class);

		if (null != searchVehicleInventoryRequest.getPurchaseFromDate() && 
				null != searchVehicleInventoryRequest.getPurchaseToDate()) {
			q.setParameter("purchaseFromDate", searchVehicleInventoryRequest.getPurchaseFromDate());
			q.setParameter("purchaseToDate", searchVehicleInventoryRequest.getPurchaseToDate());
		}
		if (null != searchVehicleInventoryRequest.getVinNum() && !searchVehicleInventoryRequest.getVinNum().isEmpty()) {
			q.setParameter("vin_num", searchVehicleInventoryRequest.getVinNum());
		}
		if (null != searchVehicleInventoryRequest.getPurchasedFrom() && !searchVehicleInventoryRequest.getPurchasedFrom().isEmpty() ) {
			q.setParameter("purchased_from", searchVehicleInventoryRequest.getPurchasedFrom());
		}
		if (null != searchVehicleInventoryRequest.getEngineNum() && !searchVehicleInventoryRequest.getEngineNum().isEmpty()) {
			q.setParameter("engine_num", searchVehicleInventoryRequest.getEngineNum());
		}
		if (null != searchVehicleInventoryRequest.getPurchaseInvoiceNum() && !searchVehicleInventoryRequest.getPurchaseInvoiceNum().isEmpty()) {
			q.setParameter("purchase_invoice_num", searchVehicleInventoryRequest.getPurchaseInvoiceNum());
		}
		if (null != searchVehicleInventoryRequest.getLocation() && !searchVehicleInventoryRequest.getLocation().isEmpty()) {
			q.setParameter("location", searchVehicleInventoryRequest.getLocation());
		}
		if (null != searchVehicleInventoryRequest.getDealerCode() && !searchVehicleInventoryRequest.getDealerCode().isEmpty()) {
			q.setParameter("dealer_code", searchVehicleInventoryRequest.getLocation());
		}
		if (null != searchVehicleInventoryRequest.getVehicleFinancedBy() && !searchVehicleInventoryRequest.getVehicleFinancedBy().isEmpty()) {
			q.setParameter("vehicle_financed_by", searchVehicleInventoryRequest.getVehicleFinancedBy());
		}
		if (null != searchVehicleInventoryRequest.getStatus() && !searchVehicleInventoryRequest.getStatus().isEmpty()) {
			q.setParameter("status", searchVehicleInventoryRequest.getStatus());
		}
		if (null != searchVehicleInventoryRequest.getOrgId() && !searchVehicleInventoryRequest.getOrgId().isEmpty()) {
			q.setParameter("org_id", searchVehicleInventoryRequest.getOrgId());
		}
		if(null != searchVehicleInventoryRequest.getLimit() &&  !searchVehicleInventoryRequest.getLimit().isEmpty() && 
				null != searchVehicleInventoryRequest.getOffset() && !searchVehicleInventoryRequest.getOffset().isEmpty()) {
			q.setParameter("limit", Integer.parseInt(searchVehicleInventoryRequest.getLimit()));
			q.setParameter("offset", Integer.parseInt(searchVehicleInventoryRequest.getOffset()));
			
		}
		queryResults = q.getResultList();

		if (null != queryResults && queryResults.size() > 0) {

			queryResults.stream().forEach(entity -> modelList.add(mapper.mapEntityToModel(entity)));
		}
		response.setStockInventoryList(modelList);
		return response;
	}

	private String getAndCondition(boolean andConditionAdded) {

		String condition = " ";
		if (andConditionAdded) {
			condition = " and ";
		}
		return condition;

	}

	@Override
	public SearchVehicleInventoryResponse getVehicleInventory(int orgId, int limit, int offset) {
		Pageable pageable = PageRequest.of(offset, limit);
		List<VehicleStockInventoryEntity> entities = null;
		List<AddVehicleInventoryRequest> model = new ArrayList<>();
		entities = repo.getVehicleInventory(orgId, limit, offset);
		SearchVehicleInventoryResponse response = new SearchVehicleInventoryResponse();
		if (null != entities && entities.size() > 0) {
			entities.stream().forEach(entity -> model.add(mapper.mapEntityToModel(entity)));
			response.setStockInventoryList(model);
		}
		return response;
	}

}
