package huan.yan.myproject.zabbix;

public class MmMib {
    private String name;
    private String value;
    private String parentValue;
    private String syntax;
    private String access;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
