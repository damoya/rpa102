package org.elyeva.rpa102.cltfx.config.funct;

import es.indra.eplatform.EPException;
import es.indra.eplatform.funct.IFunctProvider;

public interface IConfigFunctProvider extends IFunctProvider {

	String getConfigValue(String key) throws EPException;

}
