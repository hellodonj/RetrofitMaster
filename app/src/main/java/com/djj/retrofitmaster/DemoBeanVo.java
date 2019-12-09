package com.djj.retrofitmaster;

/**
 * desc:需求数据定义
 * author:djj
 * date:2019/12/8 15:07
 */
public class DemoBeanVo {

    /**
     * id : 1001
     * name : sss
     * appid : 1022
     * showtype : text
     */

    private String id;
    private String name;
    private String appid;
    private String showtype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }
}
