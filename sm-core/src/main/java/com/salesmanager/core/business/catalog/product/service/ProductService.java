package com.salesmanager.core.business.catalog.product.service;

import java.io.File;
import java.util.List;
import java.util.Locale;

import com.salesmanager.core.business.catalog.category.model.Category;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.ProductCriteria;
import com.salesmanager.core.business.catalog.product.model.ProductList;
import com.salesmanager.core.business.catalog.product.model.description.ProductDescription;
import com.salesmanager.core.business.catalog.product.model.image.ProductImage;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.modules.cms.OutputContentImage;

public interface ProductService extends SalesManagerEntityService<Long, Product> {

	void addProductDescription(Product product, ProductDescription description) throws ServiceException;
	
	ProductDescription getProductDescription(Product product, Language language);
	
	Product getProductForLocale(long productId, Language language, Locale locale) throws ServiceException;
	
	List<Product> getProductsForLocale(Category category, Language language, Locale locale) throws ServiceException;
	
	/**
	 * Returns a List o product for pagination
	 * @param category
	 * @param language
	 * @param locale
	 * @param startIndex
	 * @param maxCount
	 * @return
	 * @throws ServiceException
	 */
	ProductList getProductsForLocale(Category category, Language language, Locale locale, int startIndex, int maxCount) throws ServiceException;

	List<Product> getProducts(List<Long> categoryIds) throws ServiceException;

	/**
	 * Add a ProductImage to the persistence and an entry to the CMS
	 * @param product
	 * @param productImage
	 * @param file
	 * @throws ServiceException
	 */
	void addProductImage(Product product, ProductImage productImage, File file)
			throws ServiceException;

	/**
	 * Get the image ByteArrayOutputStream and content description from CMS
	 * @param productImage
	 * @return
	 * @throws ServiceException
	 */
	OutputContentImage getProductImage(ProductImage productImage)
			throws ServiceException;

	/**
	 * Returns all Images for a given product
	 * @param product
	 * @return
	 * @throws ServiceException
	 */
	List<OutputContentImage> getProductImages(Product product)
			throws ServiceException;

	ProductList listByStore(MerchantStore store, Language language,
			ProductCriteria criteria);
}
