package com.salesmanager.web.admin.controller.tax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.tax.model.TaxConfiguration;
import com.salesmanager.core.business.tax.service.TaxService;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;

@Controller
public class TaxConfigurationController {
	
	@Autowired
	private TaxService taxService = null;
	
	
	@Secured("TAX")
	@RequestMapping(value={"/admin/tax/taxconfiguration/edit.html"}, method=RequestMethod.GET)
	public String displayTaxConfiguration(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model, request);
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		
		TaxConfiguration taxConfiguration = taxService.getTaxConfiguration(store);
		if(taxConfiguration == null) {
			
			taxConfiguration = new TaxConfiguration();
			
		}
		
		model.addAttribute("taxConfiguration", taxConfiguration);
		
		return com.salesmanager.web.admin.controller.ControllerConstants.Tiles.Tax.taxConfiguration;
	}
	
	
	private void setMenu(Model model, HttpServletRequest request)
	throws Exception {

		// display menu
		Map<String, String> activeMenus = new HashMap<String, String>();
		activeMenus.put("tax", "tax");
		activeMenus.put("taxconfiguration", "taxconfiguration");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>) request
				.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu) menus.get("tax");
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("activeMenus", activeMenus);
		//

	}

}