/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Angga Suta
 */
public final class PaginationServices {
    
    private int totalPage;
    private int currentPage;
    private int start;
    private int totalData;
    private int recordToGet;
    private int command;
    
    public final static int FIRST = 0;
    public final static int PREV = 1;
    public final static int NEXT = 2;
    public final static int LAST = 3;
    
    public PaginationServices(int total, int dataLimit, int page, int nav){
        this.totalData = total;
        this.recordToGet = dataLimit;
        this.currentPage = page;
        this.command = nav;
        
        this.generatePaginationNumber();
    }
    
    public void generatePaginationNumber(){
        if(this.currentPage>1){
            this.start = (this.currentPage-1) * this.recordToGet;
        }
        this.totalPage = (int) Math.ceil(((double)this.getTotalData()/(double)this.getRecordToGet()));
        this.actionList();
    }
    
    public void actionList() {
        switch(this.command){                
            case FIRST :                
                this.start = 0;
                this.currentPage = 1;
                break;

            case PREV :                
                this.start = this.start - this.recordToGet;
                this.currentPage = this.currentPage - 1;
                if(this.start < 0){
                    this.start = 0;
                    this.currentPage = 1;
                }                
                break;

            case NEXT :
                this.start = this.start + this.recordToGet;
                this.currentPage += 1;
                if(this.start >= this.totalData){
                    this.start = this.start - this.recordToGet;
                    this.currentPage -= 1;
                }                
                break;

            case LAST :
                int mdl = this.totalData % this.recordToGet;
                if(mdl>0){
                    this.start = this.totalData - mdl;
                }
                else{
                    this.start = this.totalData - this.recordToGet;
                }
                this.currentPage = this.totalPage;
                break;

            default:
                this.start = this.start;
                if(this.totalData<1)
                    this.start = 0;                

                if(start > this.totalData){
                    mdl = this.totalData % recordToGet;
                    if(mdl>0){
                        this.start = this.totalData - mdl;
                    }
                    else{
                        this.start = this.totalData - recordToGet;
                    }                
                }                
                break;
        }
    }
    
    
    /**
     * @return the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the totalData
     */
    public int getTotalData() {
        return totalData;
    }

    /**
     * @param aTotalData the totalData to set
     */
    public void setTotalData(int aTotalData) {
        totalData = aTotalData;
    }

    /**
     * @return the recordToGet
     */
    public int getRecordToGet() {
        return recordToGet;
    }

    /**
     * @param aRecordToGet the recordToGet to set
     */
    public void setRecordToGet(int aRecordToGet) {
        recordToGet = aRecordToGet;
    }

    /**
     * @return the command
     */
    public int getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(int command) {
        this.command = command;
    }
    
    
}
