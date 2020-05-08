/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.master;

/**
 *
 * @author Angga Suta
 */
public class RoleDetail {
    private long roleDetailId;
    private long roleId;
    private String kodeMenu;
    private String akses;

    /**
     * @return the roleDetailId
     */
    public long getRoleDetailId() {
        return roleDetailId;
    }

    /**
     * @param roleDetailId the roleDetailId to set
     */
    public void setRoleDetailId(long roleDetailId) {
        this.roleDetailId = roleDetailId;
    }

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
     * @return the kodeMenu
     */
    public String getKodeMenu() {
        return kodeMenu;
    }

    /**
     * @param kodeMenu the kodeMenu to set
     */
    public void setKodeMenu(String kodeMenu) {
        this.kodeMenu = kodeMenu;
    }

    /**
     * @return the akses
     */
    public String getAkses() {
        return akses;
    }

    /**
     * @param akses the akses to set
     */
    public void setAkses(String akses) {
        this.akses = akses;
    }
    
    
}
