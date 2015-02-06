/**
    Copyright (C) 2014  AIT / Austrian Institute of Technology
    http://www.ait.ac.at

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */
package at.ac.ait.ubicity.ubicity.facebook.impl;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;

import org.apache.log4j.Logger;

import at.ac.ait.ubicity.commons.broker.BrokerProducer;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;
import at.ac.ait.ubicity.commons.util.PropertyLoader;
import at.ac.ait.ubicity.ubicity.facebook.FBPlugin;

@PluginImplementation
public class FBPluginImpl extends BrokerProducer implements FBPlugin {

	private String name;

	private String esIndex;

	volatile boolean shutdown = false;

	final static Logger logger = Logger.getLogger(FBPluginImpl.class);

	@Override
	@Init
	public void init() {
		PropertyLoader config = new PropertyLoader(
				FBPluginImpl.class.getResource("/facebook.cfg"));
		setProducerSettings(config);
		setPluginConfig(config);

		logger.info(name + " loaded");
	}

	/**
	 * Sets the Apollo broker settings
	 * 
	 * @param config
	 */
	private void setProducerSettings(PropertyLoader config) {
		try {
			super.init(config.getString("plugin.fb.broker.user"),
					config.getString("plugin.fb.broker.pwd"));
			setProducer(config.getString("plugin.fb.broker.dest"));

		} catch (UbicityBrokerException e) {
			logger.error("During init caught exc.", e);
		}
	}

	/**
	 * Sets the Plugin configuration.
	 * 
	 * @param config
	 */
	private void setPluginConfig(PropertyLoader config) {
		this.name = config.getString("plugin.fb.name");
		esIndex = config.getString("plugin.fb.elasticsearch.index");
	}

	@Override
	public String getName() {
		return this.name;
	}
}
