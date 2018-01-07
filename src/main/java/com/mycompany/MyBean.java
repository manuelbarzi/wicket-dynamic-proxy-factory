package com.mycompany;

/**
 * Dummy bean implementation.
 */
@SuppressWarnings("serial")
public class MyBean implements IMyBean {
	public MyBean(String data) {
		setData(data);
	}

	private String data;

	/* (non-Javadoc)
	 * @see com.mycompany.IMyBean#getData()
	 */
	@Override
	public String getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see com.mycompany.IMyBean#setData(java.lang.String)
	 */
	@Override
	public void setData(String data) {
		this.data = data;
	}
	
}
