package com.areef.asrapro.ModelClasses;

public class SubCategoryModel {

    private String subCategoryIconLink, subCategoryName;

    public SubCategoryModel(String subCategoryIconLink, String subCategoryName) {
        this.subCategoryIconLink = subCategoryIconLink;
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryIconLink() {
        return subCategoryIconLink;
    }

    public void setSubCategoryIconLink(String subCategoryIconLink) {
        this.subCategoryIconLink = subCategoryIconLink;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

}
