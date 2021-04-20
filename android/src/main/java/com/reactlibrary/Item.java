package com.reactlibrary;

import com.emeint.android.fawryplugin.Plugininterfacing.PayableItem;

import java.io.Serializable;




public class Item implements PayableItem, Serializable {
    private String description ;
    private String price;
    private String sku ;
    private String qty ;


    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String getFawryItemDescription() {
        return description;
    }

    @Override
    public String getFawryItemSKU() {
        return sku;
    }

    @Override
    public String getFawryItemPrice() {
        return price;
    }

    @Override
    public String getFawryItemQuantity() {
        return qty;
    }

    @Override
    public String getFawryItemVariantCode() {
        return null;
    }

    @Override
    public String[] getFawryItemReservationCodes() {
        return new String[0];
    }

    @Override
    public String getFawryItemHeight() {
        return null;
    }

    @Override
    public String getFawryItemWidth() {
        return null;
    }

    @Override
    public String getFawryItemLength() {
        return null;
    }

    @Override
    public String getFawryItemWeight() {
        return null;
    }

    @Override
    public String getFawryItemEarningRuleID() {
        return null;
    }

    @Override
    public String getFawryItemOriginalPrice() {
        return null;
    }
}
