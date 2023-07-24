package org.lamisplus.modules.ml;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;

@AcrossApplication(
		modules = {
				AcrossHibernateJpaModule.NAME,
		})
public class MachineLearningModule extends AcrossModule {

	public  static final String NAME = "MachineLearningModule";

	public MachineLearningModule() {
		super ();
		addApplicationContextConfigurer (new ComponentScanConfigurer(
				getClass ().getPackage ().getName () + ".repositories",
				getClass ().getPackage ().getName () + ".service",
				getClass ().getPackage ().getName () + ".mapper",
				getClass ().getPackage ().getName () + ".controller"
		));
	}

	@Override
	public String getName() {
		return NAME;
	}
}
