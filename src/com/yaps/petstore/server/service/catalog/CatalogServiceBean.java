package com.yaps.petstore.server.service.catalog;

import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.category.CategoryLocal;
import com.yaps.petstore.server.domain.category.CategoryLocalHome;
import com.yaps.petstore.server.domain.item.ItemLocal;
import com.yaps.petstore.server.domain.item.ItemLocalHome;
import com.yaps.petstore.server.domain.product.ProductLocal;
import com.yaps.petstore.server.domain.product.ProductLocalHome;
import com.yaps.petstore.server.service.AbstractRemoteService;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a facade for all catalog services.
 */
public class CatalogServiceBean extends AbstractRemoteService {

    // ======================================
    // =            Constructors            =
    // ======================================
    public CatalogServiceBean() {
    }

    // ======================================
    // =      Category Business methods     =
    // ======================================
    public CategoryDTO createCategory(final CategoryDTO categoryDTO) throws CreateException, CheckException {
        final String mname = "createCategory";
        Trace.entering(getCname(), mname, categoryDTO);

        if (categoryDTO == null)
            throw new CheckException("Category object is null");

        // Creates the object
        final CategoryLocal category = getCategoryHome().create(categoryDTO.getId(), categoryDTO.getName(), categoryDTO.getDescription());

        // Transforms domain object into DTO
        final CategoryDTO result = transformCategory2DTO(category);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public CategoryDTO findCategory(final String categoryId) throws FinderException, CheckException {
        final String mname = "findCategory";
        Trace.entering(getCname(), mname, categoryId);

        if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid id");

        final CategoryLocal category;

        // Finds the object
        category = getCategoryHome().findByPrimaryKey(categoryId);

        // Transforms domain object into DTO
        final CategoryDTO categoryDTO = transformCategory2DTO(category);

        Trace.exiting(getCname(), mname, categoryDTO);
        return categoryDTO;
    }

    public void deleteCategory(final String categoryId) throws RemoveException, CheckException {
        final String mname = "deleteCategory";
        Trace.entering(getCname(), mname, categoryId);

        if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid id");

        final CategoryLocal category;

        // Checks if the object exists
        try {
            category = getCategoryHome().findByPrimaryKey(categoryId);
        } catch (FinderException e) {
            throw new CheckException("Category must exist to be deleted");
        }

        // Deletes the object
        category.remove();
    }

    public void updateCategory(final CategoryDTO categoryDTO) throws CheckException {
        final String mname = "updateCategory";
        Trace.entering(getCname(), mname, categoryDTO);

        if (categoryDTO == null)
            throw new CheckException("Category object is null");
        if (categoryDTO.getId() == null || "".equals(categoryDTO.getId()))
            throw new CheckException("Invalid id");

        final CategoryLocal category;

        // Checks if the object exists
        try {
            category = getCategoryHome().findByPrimaryKey(categoryDTO.getId());
        } catch (FinderException e) {
            throw new CheckException("Category must exist to be updated");
        }

        // Transforms DTO into domain object
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // Checks data integrity
        category.checkData();
    }

    public Collection findCategories() throws FinderException {
        final String mname = "findCategories";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection categories = getCategoryHome().findAll();

        // Transforms domain objects into DTOs
        final Collection categoriesDTO = transformCategories2DTOs(categories);

        Trace.exiting(getCname(), mname, new Integer(categoriesDTO.size()));
        return categoriesDTO;
    }

    // ======================================
    // =      Product Business methods     =
    // ======================================
    public ProductDTO createProduct(final ProductDTO productDTO) throws CreateException, CheckException {
        final String mname = "createProduct";
        Trace.entering(getCname(), mname, productDTO);

        if (productDTO == null)
            throw new CheckException("Product object is null");
        if (productDTO.getCategoryId() == null || "".equals(productDTO.getCategoryId()))
            throw new CheckException("Invalid category id");

        // Finds the linked object
        final CategoryLocal category;
        try {
            category = getCategoryHome().findByPrimaryKey(productDTO.getCategoryId());
        } catch (FinderException e) {
            throw new CheckException("Category must exist to create a product");
        }

        // Creates the object
        final ProductLocal product = getProductHome().create(productDTO.getId(), productDTO.getName(), productDTO.getDescription(), category);

        // Transforms domain object into DTO
        final ProductDTO result = transformProduct2DTO(product);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public ProductDTO findProduct(final String productId) throws FinderException, CheckException {
        final String mname = "findProduct";
        Trace.entering(getCname(), mname, productId);

        if (productId == null || "".equals(productId))
            throw new CheckException("Invalid id");

        final ProductLocal product;

        // Finds the object
        product = getProductHome().findByPrimaryKey(productId);

        // Transforms domain object into DTO
        final ProductDTO productDTO = transformProduct2DTO(product);

        Trace.exiting(getCname(), mname, productDTO);
        return productDTO;
    }

    public void deleteProduct(final String productId) throws RemoveException, CheckException {
        final String mname = "deleteProduct";
        Trace.entering(getCname(), mname, productId);

        if (productId == null || "".equals(productId))
            throw new CheckException("Invalid id");

        final ProductLocal product;

        // Checks if the object exists
        try {
            product = getProductHome().findByPrimaryKey(productId);
        } catch (FinderException e) {
            throw new CheckException("Product must exist to be deleted");
        }

        // Deletes the object
        product.remove();
    }

    public void updateProduct(final ProductDTO productDTO) throws CheckException {
        final String mname = "updateProduct";
        Trace.entering(getCname(), mname, productDTO);

        if (productDTO == null)
            throw new CheckException("Product object is null");
        if (productDTO.getId() == null || "".equals(productDTO.getId()))
            throw new CheckException("Invalid id");

        final ProductLocal product;

        // Checks if the object exists
        try {
            product = getProductHome().findByPrimaryKey(productDTO.getId());
        } catch (FinderException e) {
            throw new CheckException("Product must exist to be updated");
        }

        // Finds the linked object
        final CategoryLocal category;
        try {
            category = getCategoryHome().findByPrimaryKey(productDTO.getCategoryId());
        } catch (FinderException e) {
            throw new CheckException("Category must exist to update a product");
        }

        // Transforms DTO into domain object
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        // Checks data integrity
        product.checkData();
    }

    public Collection findProducts() throws FinderException {
        final String mname = "findProducts";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection products = getProductHome().findAll();

        // Transforms domain objects into DTOs
        final Collection productsDTO = transformProducts2DTOs(products);

        Trace.exiting(getCname(), mname, new Integer(productsDTO.size()));
        return productsDTO;
    }

    public Collection findProducts(final String categoryId) throws FinderException, CheckException {
        final String mname = "findProducts";
        Trace.entering(getCname(), mname, categoryId);

        if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid category id");

        // Finds all the products
        final Collection products = getCategoryHome().findByPrimaryKey(categoryId).getProducts();

        // Transforms domain objects into DTOs
        final Collection productsDTO = transformProducts2DTOs(products);

        Trace.exiting(getCname(), mname, new Integer(productsDTO.size()));
        return productsDTO;
    }

    // ======================================
    // =        Item Business methods       =
    // ======================================
    public ItemDTO createItem(final ItemDTO itemDTO) throws CreateException, CheckException {
        final String mname = "createItem";
        Trace.entering(getCname(), mname, itemDTO);

        if (itemDTO == null)
            throw new CheckException("Item object is null");
        if (itemDTO.getProductId() == null || "".equals(itemDTO.getProductId()))
            throw new CheckException("Invalid product id");

        // Finds the linked object
        final ProductLocal product;
        try {
            product = getProductHome().findByPrimaryKey(itemDTO.getProductId());
        } catch (FinderException e) {
            throw new CheckException("Product must exist to create an item");
        }

        // Creates the object
        final ItemLocal item = getItemHome().create(itemDTO.getId(), itemDTO.getName(), itemDTO.getUnitCost(), product);
        item.setImagePath(itemDTO.getImagePath());

        // Transforms domain object into DTO
        final ItemDTO result = transformItem2DTO(item);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public ItemDTO findItem(final String itemId) throws FinderException, CheckException {
        final String mname = "findItem";
        Trace.entering(getCname(), mname, itemId);

        if (itemId == null || "".equals(itemId))
            throw new CheckException("Invalid id");

        final ItemLocal item;

        // Finds the object
        item = getItemHome().findByPrimaryKey(itemId);

        // Transforms domain object into DTO
        final ItemDTO itemDTO = transformItem2DTO(item);

        Trace.exiting(getCname(), mname, itemDTO);
        return itemDTO;
    }

    public void deleteItem(final String itemId) throws RemoveException, CheckException {
        final String mname = "deleteItem";
        Trace.entering(getCname(), mname, itemId);

        if (itemId == null || "".equals(itemId))
            throw new CheckException("Invalid id");

        final ItemLocal item;

        // Checks if the object exists
        try {
            item = getItemHome().findByPrimaryKey(itemId);
        } catch (FinderException e) {
            throw new CheckException("Item must exist to be deleted");
        }

        // Deletes the object
        item.remove();
    }

    public void updateItem(final ItemDTO itemDTO) throws CheckException {
        final String mname = "updateItem";
        Trace.entering(getCname(), mname, itemDTO);

        if (itemDTO == null)
            throw new CheckException("Item object is null");
        if (itemDTO.getId() == null || "".equals(itemDTO.getId()))
            throw new CheckException("Invalid id");

        final ItemLocal item;

        // Checks if the object exists
        try {
            item = getItemHome().findByPrimaryKey(itemDTO.getId());
        } catch (FinderException e) {
            throw new CheckException("Item must exist to be updated");
        }

        // Finds the linked object
        final ProductLocal product;
        try {
            product = getProductHome().findByPrimaryKey(itemDTO.getProductId());
        } catch (FinderException e) {
            throw new CheckException("Product must exist to update an item");
        }

        // Transforms DTO into domain object
        item.setName(itemDTO.getName());
        item.setUnitCost(itemDTO.getUnitCost());
        item.setImagePath(itemDTO.getImagePath());
        item.setProduct(product);

        // Checks data integrity
        item.checkData();
    }

    public Collection findItems() throws FinderException {
        final String mname = "findItems";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection items = getItemHome().findAll();

        // Transforms domain objects into DTOs
        final Collection itemsDTO = transformItems2DTOs(items);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    public Collection findItems(final String productId) throws FinderException, CheckException {
        final String mname = "findItems";
        Trace.entering(getCname(), mname, productId);

        if (productId == null || "".equals(productId))
            throw new CheckException("Invalid product id");

        // Finds all the items
        final Collection items = getProductHome().findByPrimaryKey(productId).getItems();

        // Transforms domain objects into DTOs
        final Collection itemsDTO = transformItems2DTOs(items);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    public Collection searchItems(final String keyword) throws FinderException {
        final String mname = "searchItems";
        Trace.entering(getCname(), mname, keyword);

        // Search all the items
        final Collection itemsDTO = new ItemDAO().search(keyword);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    // Category
    private CategoryDTO transformCategory2DTO(final CategoryLocal category) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    private Collection transformCategories2DTOs(final Collection categories) {
        final Collection categoriesDTO = new ArrayList();
        for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
            final CategoryLocal category = (CategoryLocal) iterator.next();
            categoriesDTO.add(transformCategory2DTO(category));
        }
        return categoriesDTO;
    }

    // Product
    private ProductDTO transformProduct2DTO(final ProductLocal product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        // Retreives the data for the linked object
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }

    private Collection transformProducts2DTOs(final Collection products) {
        final Collection productsDTO = new ArrayList();
        for (Iterator iterator = products.iterator(); iterator.hasNext();) {
            final ProductLocal product = (ProductLocal) iterator.next();
            productsDTO.add(transformProduct2DTO(product));
        }
        return productsDTO;
    }

    // Item
    private ItemDTO transformItem2DTO(final ItemLocal item) {
        final ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUnitCost(item.getUnitCost());
        itemDTO.setImagePath(item.getImagePath());
        // Retreives the data for the linked object
        itemDTO.setProductId(item.getProduct().getId());
        itemDTO.setProductName(item.getProduct().getName());
        itemDTO.setProductDescription(item.getProduct().getDescription());
        return itemDTO;
    }

    private Collection transformItems2DTOs(final Collection items) {
        final Collection itemsDTO = new ArrayList();
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            final ItemLocal item = (ItemLocal) iterator.next();
            itemsDTO.add(transformItem2DTO(item));
        }
        return itemsDTO;
    }

    private CategoryLocalHome getCategoryHome() {
        return (CategoryLocalHome) new ServiceLocator().getLocalHome(CategoryLocalHome.JNDI_NAME);
    }

    private ProductLocalHome getProductHome() {
        return (ProductLocalHome) new ServiceLocator().getLocalHome(ProductLocalHome.JNDI_NAME);
    }

    private ItemLocalHome getItemHome() {
        return (ItemLocalHome) new ServiceLocator().getLocalHome(ItemLocalHome.JNDI_NAME);
    }
}
