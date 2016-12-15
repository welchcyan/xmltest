package io.pivotal.willchen.domain;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by chenw13 on 09/12/2016.
 */
@Component
@XmlRootElement(name="person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private int age;

    private List<Test> test;

    public Person() {

    }

    private String id;

    private String sir;

    public String getSir() {
        return sir;
    }

    public List<Test> getTest() {
        return test;
    }

    public void setTest(List<Test> test) {
        this.test = test;
    }

    public void setSir(String sir) {
        this.sir = sir;
    }

    private String name;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "<person><id>"+id+"</id><name>"+name+"</name></person>";
    }
}
