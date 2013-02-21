package com.salesmanager.web.admin.controller.shipping;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.shipping.model.ShippingConfiguration;
import com.salesmanager.core.business.shipping.model.ShippingType;
import com.salesmanager.core.business.shipping.service.ShippingService;
import com.salesmanager.core.utils.ProductPriceUtils;
import com.salesmanager.web.admin.controller.ControllerConstants;
import com.salesmanager.web.admin.entity.web.Menu;
import com.salesmanager.web.constants.Constants;
import com.salesmanager.web.utils.LabelUtils;

@Controller
public class ShippingPackagingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingPackagingController.class);
	

	@Autowired
	private ShippingService shippingService;
	
	@Autowired
	LabelUtils messages;
	
	@Autowired
	private ProductPriceUtils priceUtil;
	
	/**
	 * Displays shipping packaging
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/shipping/shippingPackaging.html", method=RequestMethod.GET)
	public String displayShippingPackaging(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {


		this.setMenu(model, request);

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		

		
		ShippingConfiguration shippingConfiguration =  shippingService.getShippingConfiguration(store);
		
		if(shippingConfiguration==null) {
			shippingConfiguration = new ShippingConfiguration();
			shippingConfiguration.setShippingType(ShippingType.INTERNATIONAL);
		}

		model.addAttribute("configuration", shippingConfiguration);
		return ControllerConstants.Tiles.Shipping.shippingPackaging;
		
		
	}
	
	/**
	 * Saves shipping packaging
	 * @param configuration
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/shipping/saveShippingPackaging.html", method=RequestMethod.POST)
	public String saveShippingOptions(@ModelAttribute("configuration") ShippingConfiguration configuration, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {


		this.setMenu(model, request);
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		//get original configuration
		ShippingConfiguration shippingConfiguration =  shippingService.getShippingConfiguration(store);
		
		if(shippingConfiguration==null) {
			shippingConfiguration = new ShippingConfiguration();
		}
		
/*		BigDecimal submitedOrderPrice = null;
		if(configuration.getOrderTotalFreeShippingText()!=null){
			try {
				submitedOrderPrice = priceUtil.getAmount(configuration.getOrderTotalFreeShippingText());
				shippingConfiguration.setOrderTotalFreeShipping(submitedOrderPrice);
			} catch (Exception e) {
				ObjectError error = new ObjectError("orderTotalFreeShippingText",messages.getMessage("message.invalid.price", locale));
				result.addError(error);
			}
		}
		
		BigDecimal submitedHandlingPrice = null;
		if(configuration.getHandlingFeesText()!=null){
			try {
				submitedHandlingPrice = priceUtil.getAmount(configuration.getHandlingFeesText());
				shippingConfiguration.setHandlingFees(submitedHandlingPrice);
			} catch (Exception e) {
				ObjectError error = new ObjectError("handlingFeesText",messages.getMessage("message.invalid.price", locale));
				result.addError(error);
			}
		}
		
		shippingConfiguration.setFreeShippingEnabled(configuration.isFreeShippingEnabled());
		shippingConfiguration.setTaxOnShipping(configuration.isTaxOnShipping());
		shippingConfiguration.setShipFreeType(configuration.getShipFreeType());*/
		

		shippingService.saveShippingConfiguration(shippingConfiguration, store);
		
		model.addAttribute("configuration", configuration);
		model.addAttribute("success","success");
		return ControllerConstants.Tiles.Shipping.shippingPackaging;
		
		
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("shipping", "shipping");
		activeMenus.put("shipping-packaging", "shipping-packaging");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("shipping");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}


}
