
package com.rizwan.audit.dto;

public class VersionsDiffDTO {

	private String propertyName;
	private Object left;
	private Object right;
	private String propertyNameWithPath;

	public String getPropertyName() {

		return propertyName;
	}

	public void setPropertyName(String propertyName) {

		this.propertyName = propertyName;
	}

	public Object getLeft() {

		return left;
	}

	public void setLeft(Object left) {

		this.left = left;
	}

	public Object getRight() {

		return right;
	}

	public void setRight(Object right) {

		this.right = right;
	}

	public String getPropertyNameWithPath() {

		return propertyNameWithPath;
	}

	public void setPropertyNameWithPath(String propertyNameWithPath) {

		this.propertyNameWithPath = propertyNameWithPath;
	}
}
