/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.master;

import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class Role {
    private long roleId;
    private String nama;
    private Vector<RoleDetail> roleDetail;

    /**
     * @return the roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the roleDetail
     */
    public Vector<RoleDetail> getRoleDetail() {
        return roleDetail;
    }

    /**
     * @param roleDetail the roleDetail to set
     */
    public void setRoleDetail(Vector<RoleDetail> roleDetail) {
        this.roleDetail = roleDetail;
    }
    
    
}
