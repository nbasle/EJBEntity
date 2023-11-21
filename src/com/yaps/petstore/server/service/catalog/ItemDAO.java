package com.yaps.petstore.server.service.catalog;

import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class does all the database access for the class Item.
 *
 * @see com.yaps.petstore.server.domain.item.ItemBean
 */
final class ItemDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_ITEM, T_PRODUCT";
    private static final String COLUMNS = "T_ITEM.ID, T_ITEM.NAME, T_ITEM.UNITCOST, T_ITEM.IMAGEPATH," +
            "T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.DESCRIPTION";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method return all the items from the database that match a keyword.
     *
     * @param keyword
     * @return collection of Items
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException     is thrown if there's a persistent problem
     */
    public Collection search(final String keyword) throws FinderException {
        final String mname = "search";
        Trace.entering(getCname(), mname, keyword);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection items = new ArrayList();

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE (T_ITEM.ID LIKE '%" + keyword + "%' OR T_ITEM.NAME LIKE '%" + keyword + "%') AND PRODUCT_FK=T_PRODUCT.ID";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Set data to the collection
                items.add(transformResultset2DTO(resultSet));
            }

            if (items.isEmpty())
                throw new ObjectNotFoundException();

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new FinderException("Cannot get data from the database");
        } finally {
            // Close
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new FinderException("Cannot close the database connection");
            }
        }
        return items;
    }

    protected ItemDTO transformResultset2DTO(final ResultSet resultSet) throws SQLException {
        final ItemDTO itemDTO;
        itemDTO = new ItemDTO(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3));
        itemDTO.setImagePath(resultSet.getString(4));
        itemDTO.setProductId(resultSet.getString(5));
        itemDTO.setProductName(resultSet.getString(6));
        itemDTO.setProductDescription(resultSet.getString(7));
        return itemDTO;
    }
}
