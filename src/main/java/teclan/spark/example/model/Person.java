package teclan.spark.example.model;

import teclan.utils.GsonUtils;

public class Person {
    private int    id;
    private String name;
    private int    age;
    private String sex;

    public Person() {

    }

    public Person(int id, String name, int age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public void update(String newName, int newAge, String newSex) {
        this.name = newName;
        this.age = newAge;
        this.sex = newSex;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }

}
