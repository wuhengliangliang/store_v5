package cn.itcast.store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	private String oid; //订单编号
	private Date ordertime; //订单时间
	private double total; //总计金额
	private int state; //下单状态
	private String address; //收货地址
	private String name; //收货人的名字
	private String telephone; //收货人的电话
	//private String uid;
	//1、程序对象和对象发生关系，而不是对象和对象的属性发生关系
	//2、设计Order的目的是为了让order携带订单上的数据向service，dao传递
	private User user;
	//从订单看订单上有多少项
	//程序中体现订单对象和订单之间的关系，我们在项目中的部分功能中有类似的需求，查询订单的同时还需要获取订单下所有的订单项
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getList() {
		return list;
	}

	public void setList(List<OrderItem> list) {
		this.list = list;
	}

	private List<OrderItem>list=new ArrayList<OrderItem>();

	

}
