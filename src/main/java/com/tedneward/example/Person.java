package com.tedneward.example;

import java.beans.*;
import java.util.*;
import java.lang.*;

public class Person implements Comparable<Person> {
  private static int personCount;
  private int age;
  private String name;
  private double salary;
  private String ssn;
  private boolean propertyChangeFired = false;
  
  public Person() {
    this("", 0, 0.0d);
  }
  
  public Person(String n, int a, double s) {
    personCount++;
    name = n;
    age = a;
    salary = s;
    /*
    Added this line because in addPropertyChange() in Test file
    the old value will be null instead of "". 
    assertEquals("", pce.getOldValue()); //pce.getOldValue = null without ssn = "" here.
    */
    ssn = ""; 
  }

  /*
    Getters and setters
  */
  public int getAge() {
    return age;
  }
  public void setAge(int age){
    if (age < 0){
      throw new IllegalArgumentException("Age must be greater than 0.");
    } else {
      this.age = age;
    }
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name){
    if(name == null || name.trim().isEmpty()){
      throw new IllegalArgumentException("Name must not be blank.");
    } else {
      this.name = name;
    }
  }
  
  public double getSalary() {
    return salary;
  }
  public void setSalary(double salary){
    this.salary = salary;
  }
  
  public String getSSN() {
    return ssn;
  }
  public void setSSN(String value) {
    String old = ssn;
    ssn = value;
    
    this.pcs.firePropertyChange("ssn", old, value);
    propertyChangeFired = true;
  }

  /*
    Person count method
  */
  public int count(){
    return personCount;
  }

  public boolean getPropertyChangeFired() {
    return propertyChangeFired;
  }

  public double calculateBonus() {
    return salary * 1.10;
  }
  
  public String becomeJudge() {
    return "The Honorable " + name;
  }
  
  public int timeWarp() {
    return age + 10;
  }
  
  @Override
  public boolean equals(Object other) {
    if(other instanceof Person) {
      Person p = (Person) other;
      return (this.name.equals(p.name) && this.age == p.age);
    }
    return false;
  }

  @Override
  public String toString() {
    // [Person name:Fird Birfle age:20 salary:195750.22]
    return "[" +
            "Person name:" + name + " " +
            "age:" + age + " " +
            "salary:" + salary + 
            "]";
  }

  // Comparable requires compareTo
  public int compareTo(Person other){
    return (int) (other.salary - this.salary);
  }

  public static ArrayList<Person> getNewardFamily(){
    ArrayList<Person> family = new ArrayList<Person>();
    family.add(new Person("Ted", 41, 250000));
    family.add(new Person("Charlotte", 43, 150000));
    family.add(new Person("Michael", 22, 10000));
    family.add(new Person("Matthew", 15, 0));
    return family;
  }

  //AgeComparator Class
  public static class AgeComparator implements Comparator<Person> {
    @Override
    public int compare(Person a, Person b){
      return a.getAge() - b.getAge();
    }
  }

  // PropertyChangeListener support; you shouldn't need to change any of
  // these two methods or the field
  //
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.pcs.addPropertyChangeListener(listener);
  }
  public void removePropertyChangeListener(PropertyChangeListener listener) {
      this.pcs.removePropertyChangeListener(listener);
  }
}
