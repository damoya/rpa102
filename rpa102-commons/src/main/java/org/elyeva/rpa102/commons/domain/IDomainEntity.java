package org.elyeva.rpa102.commons.domain;

import java.io.Serializable;

import es.indra.eplatform.util.IIdentificableObject;

public interface IDomainEntity extends IIdentificableObject, Serializable {

	void setId(String id);

}
