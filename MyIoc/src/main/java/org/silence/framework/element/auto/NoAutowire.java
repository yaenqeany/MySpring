package org.silence.framework.element.auto;

public class NoAutowire implements Autowire {
    private String type;
    public NoAutowire(String type) {
        this.type = type;
    }
    /**
     * 直接返回no，表示不需要自动装配
     */
    public String getType() {
        return "no";
    }

}
