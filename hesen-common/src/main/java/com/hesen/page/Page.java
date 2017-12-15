package com.hesen.page;
import java.io.Serializable;
import java.util.List;

import com.hesen.exception.OwnException;

/**
 * 分页基本实现
 * 
 */
public class Page<T> implements Pagination,Serializable  {

	private static final long serialVersionUID = 4459484901491653790L;
	/** 页码 */
	protected int currentPage;
	/** 每页记录条数 */
	protected int pageSize=5;
	/** 总页数 */
	protected int totalPage;
	/** 总记录条数 */
	protected int totalCount = 0;

	/** 用于存放查询结果 */
	protected List<T> rows;

	public Page(int currentPage, int pageSize) {
		if (currentPage <= 0) {
			throw new IllegalArgumentException("currentPage must be greater than 0.");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("pageSize must be greater than 0.");
		}
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Page(){
		
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount < 0) { // 如果总数为负数, 表未设置
			totalPage = 0;
		} else { // 计算总页数
			totalPage = (totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1);
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			throw new OwnException("当前页必须是正整数");
		}
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotalCount() {
		return totalCount;
	}
}