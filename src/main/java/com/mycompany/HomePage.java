package com.mycompany;

import org.apache.wicket.Session;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "serial", "rawtypes" })
public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(HomePage.class);
	
	private static String INVALID_DATA_SUBMISSION = "Invalid data submitted!";

	public HomePage(final PageParameters parameters) {
		super(parameters);

		log.info("rendering home page");

		final IMyBean myBean = new MyBean("valid data");

		IMyBean myBeanProxy = DynamicProxyFactory.newInstance(new ListenerInvocationHandler<IMyBean>(myBean) {
			private long timing;

			@Override
			public void onBeforeInvocation(String methodName, Object[] args) {
				if ("setData".equals(methodName)) {
					log.info("Executing {} with args {}", methodName, args);
					
					if (args.length < 1 || args[0] != "valid data")
						throw new InvalidDataException();
				} else if ("getData".equals(methodName))
					timing = System.nanoTime();
			}

			@Override
			public void onAfterInvocation(String methodName, Object[] args, Object result) {
				if ("getData".equals(methodName))
					log.info("Executing {} finished in {} ns", methodName, System.nanoTime() - timing);
			}

			@Override
			public void onInvocationError(String methodName, Object[] args, Throwable error) {
				if (error instanceof InvalidDataException) {
					log.error(INVALID_DATA_SUBMISSION);

					Session.get().getFeedbackMessages().add(new FeedbackMessage(null, INVALID_DATA_SUBMISSION, FeedbackMessage.ERROR));
				}
			}
		});

		Form<?> form = new Form("form");
		form.add(new TextField<IMyBean>("data", new PropertyModel<IMyBean>(myBeanProxy, "data")));

		add(form);

		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
	}

	public static class InvalidDataException extends RuntimeException {
	}
}
