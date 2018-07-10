package org.silence.beans;

public class Student {
    private String name;
    private String addr;

    public void selfIntroDuction(){
        System.out.println("我的姓名是 " + name + " 我来自 " + addr);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
