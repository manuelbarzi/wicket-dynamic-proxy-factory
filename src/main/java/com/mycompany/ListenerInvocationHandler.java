package com.mycompany;

import java.lang.reflect.Method;

import com.mycompany.DynamicProxyFactory.InvocationHandler;

/**
 * Listener implementation of
 * com.mycompany.DynamicProxyFactory.InvocationHandler.
 * 
 * @author manuelbarzi
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class ListenerInvocationHandler<T> extends InvocationHandler<T> {

	public ListenerInvocationHandler(T target) {
		super(target);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		try {
			onBeforeInvocation(method.getName(), args);

			result = super.invoke(proxy, method, args);

			onAfterInvocation(method.getName(), args, result);
		} catch (Throwable error) {
			onInvocationError(method.getName(), args, error);
		}

		return result;
	}

	/**
	 * Runs before invocation.
	 * 
	 * @param methodName
	 * @param args
	 */
	public void onBeforeInvocation(String methodName, Object[] args) {
	}

	/**
	 * Runs after invocation.
	 * 
	 * @param methodName
	 * @param args
	 * @param result
	 */
	public void onAfterInvocation(String methodName, Object[] args, Object result) {
	}

	/**
	 * Runs in case error detected before (onBeforeInvocation), during (invoke), or
	 * after (onAfterInvocation), invocation.
	 * 
	 * @param methodName
	 * @param args
	 * @param error
	 */
	public void onInvocationError(String methodName, Object[] args, Throwable error) {
	}
}
