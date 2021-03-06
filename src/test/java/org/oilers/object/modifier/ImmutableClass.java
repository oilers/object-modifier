package org.oilers.object.modifier;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class ImmutableClass {
	private final String test = "test";
	private final String test_1;
	private final Boolean testBoolean = Boolean.TRUE;
	private final Long testLong = 0L;
	private final Object testObject = new Object();
	private final BigInteger testBigInteger = BigInteger.ONE;
	private final Object noAccessor = new Object();
	private final Object privateAccessor = new Object();

	public ImmutableClass() {
		test_1 = "test_1";
	}

	public String getTest() {
		return test;
	}

	public String getTest_1() {
		return test_1;
	}

	public Boolean isTestBoolean() {
		return testBoolean;
	}

	public Long getTestLong() {
		return testLong;
	}

	public Object getTestObject() {
		return testObject;
	}

	public BigInteger getTestBigInteger() {
		return testBigInteger;
	}

	private Object getPrivateAccessor() {
		return privateAccessor;
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof ImmutableClass))
			return false;
		if (this == obj)
			return true;
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
