package com.salesmanager.core.business.order.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

import com.salesmanager.core.business.generic.model.SalesManagerEntity;
import com.salesmanager.core.constants.SchemaConstant;

@Entity
@Table (name="ORDER_TOTAL" , schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class OrderTotal extends SalesManagerEntity<Long, OrderTotal> {
	private static final long serialVersionUID = -5885315557404081674L;
	
	@Id
	@Column(name = "ORDER_ACCOUNT_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ORDER_TOTAL_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column (name ="TITLE", nullable=false)
	private String title;
	
	@Column (name ="TEXT", nullable=false)
	@Type(type = "org.hibernate.type.StringClobType")
	private String text;
	
	@Column (name ="VALUE", precision=15, scale=4, nullable=false )
	private BigDecimal value;
	
	@Column (name ="MODULE", length=60 , nullable=false )
	private String module;
	
	@Column (name ="SORT_ORDER", nullable=false)
	private int sortOrder;
	
	@ManyToOne(targetEntity = Order.class)
	@JoinColumn(name = "ORDER_ID", nullable=false)
	private Order order;
	
	public OrderTotal() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}