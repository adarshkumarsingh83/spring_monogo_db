/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo.entites;
/*
 * {@inheritDoc}
*/

/**
 * @Product : Person provide implementation of the specification
 *                    provided for the ...    
 */


/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 7:04 PM
 * @see
 */

public class Person {

    private String name;
    private String age;
    private String createdDate;

    public Person() {
    }

    public Person(String name, String age, String createdDate) {
        this.name = name;
        this.age = age;
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
