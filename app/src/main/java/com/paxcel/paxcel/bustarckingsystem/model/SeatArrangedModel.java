package com.paxcel.paxcel.bustarckingsystem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SeatArrangedModel {

    @SerializedName("lower")
    @Expose
    private List<Lower> lower = null;
    @SerializedName("upper")
    @Expose
    private List<Upper> upper = null;

    public List<Lower> getLower() {
        return lower;
    }

    public void setLower(List<Lower> lower) {
        this.lower = lower;
    }

    public List<Upper> getUpper() {
        return upper;
    }

    public void setUpper(List<Upper> upper) {
        this.upper = upper;
    }


   public class Lower {

        @SerializedName("rowIndex")
        @Expose
        private Integer rowIndex;
        @SerializedName("colIndex")
        @Expose
        private Integer colIndex;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("status")
        @Expose
        private Integer status;

        public Integer getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(Integer rowIndex) {
            this.rowIndex = rowIndex;
        }

        public Integer getColIndex() {
            return colIndex;
        }

        public void setColIndex(Integer colIndex) {
            this.colIndex = colIndex;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

   public class Upper {

        @SerializedName("rowIndex")
        @Expose
        private Integer rowIndex;
        @SerializedName("colIndex")
        @Expose
        private Integer colIndex;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("status")
        @Expose
        private Integer status;

        public Integer getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(Integer rowIndex) {
            this.rowIndex = rowIndex;
        }

        public Integer getColIndex() {
            return colIndex;
        }

        public void setColIndex(Integer colIndex) {
            this.colIndex = colIndex;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }

}
