package teclan.spark.model;

import teclan.spark.model.AbstractServiceModel;

public class Person extends AbstractServiceModel {

    private int    id;
    private String name;
    private String sex;

    public Person(int id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Person update(String name, String sex) {
        this.name = name;
        this.sex = sex;
        return this;
    }

    @Override
    public String toString() {

        return String.format("name:%s sex:%s", name, sex);

    }

    public String myString() {
        return String.format(" ========= name:%s sex:%s", name, sex);
    }

    @Override
    public String handle() {
        // TO DO
        return String.format("name:%s sex:%s", name, sex);
    }

}
