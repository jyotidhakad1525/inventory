package com.dms.inventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DropdownReq {

	
	String bu;
	String dropdownType;
	String parentId;
}
