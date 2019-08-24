package com.kobuta.rakuchin.hojinzei.vo;

public class PathVo {

    private  String pathId;

    private  String pathName;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public  PathVo(String fileId, String fileName) {
        this.setPathId(fileId);
        this.setPathName(fileName);
    }
}
