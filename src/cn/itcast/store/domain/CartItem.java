package cn.itcast.store.domain;

public class CartItem {
	private Product product ;//目的是携带商品的图片的额路径，图片名称，商品价格
	private int num;//当前类别商品数量
	private double subTotal;//小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	//小计是经过计算可以获取到的
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
	
}
