package org.hicham.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
@Entity
@Table(name="Patient")
public class Patient {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column (name="Id",nullable = false)
	int id;
	@Column (name="NomEtPrenom",nullable = true)
	String nomEtPrenom;
	@Column (name="nom",nullable = false)
	String name;
	@Column (name="Prenom",nullable = false)
	String prenom;
	@Column (name="Age",nullable = false)
	int age; 
	@Column (name="Address")
	String address; 
	@Column (name="Tel")
	int tel;
	@Column (name="Teinte")
	String teinte; 
	@Column (name="Sex",nullable = false)
	String sex; 
	@Column (name="Anticident")
	String anticident;
	@Column (name="Fonction")
	String fonction;
	
	@OneToMany(targetEntity=Act.class, mappedBy="patient",cascade=CascadeType.ALL,fetch= FetchType.EAGER)
	private List<Act> actList;

	
	
	public Patient(String nomEtPrenom,String nom ,String prenom, int age, String address, int tel, String teinte, String sex, String anticident,String fonction){
		this.nomEtPrenom= nomEtPrenom;
		this.name= nom;
		this.prenom= prenom;
		this.age= age;
		this.address= address;
		this.tel= tel;
		this.teinte= teinte;
		this.sex= sex;
		this.anticident= anticident;
		this.fonction= fonction;
	}
	public Patient(){

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomEtPrenom() {
		return nomEtPrenom;
	}
	public void setNomEtPrenom(String nomEtPrenom) {
		this.nomEtPrenom = nomEtPrenom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public String getTeinte() {
		return teinte;
	}
	public void setTeinte(String teinte) {
		this.teinte = teinte;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAnticident() {
		return anticident;
	}
	public void setAnticident(String anticident) {
		this.anticident = anticident;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public List<Act> getActList() {
		return actList;
	}
	public void setActList(List<Act> actList) {
		this.actList = actList;
	}
	
	
    
	
}
