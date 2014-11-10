/*!
 * PENTAHO CORPORATION PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2002 - 2014 Pentaho Corporation (Pentaho). All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Pentaho and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Pentaho and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Pentaho is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Pentaho,
 * explicitly covering such access.
 */

package com.pentaho.di.trans.dataservice.optimization.paramgen;

import com.pentaho.di.trans.dataservice.optimization.ValueMetaResolver;

import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

/**
 * @author nhudak
 */
public class ParameterGenerationServiceProvider {
  public ParameterGenerationService getService( StepMeta stepMeta ) {
    StepMetaInterface stepMetaInterface = stepMeta.getStepMetaInterface();
    ParameterGenerationService service = null;
    ValueMetaResolver resolver = new ValueMetaResolver( getFields( stepMetaInterface ) );
    if ( stepMetaInterface instanceof TableInputMeta ) {
      service = new TableInputParameterGeneration( resolver );
    }
    return service;
  }

  private RowMeta getFields( StepMetaInterface stepMetaInterface ) {
    RowMeta rowMeta = new RowMeta();
    try {
      stepMetaInterface.getFields( rowMeta, "", null, null, null, null, null );
    } catch ( KettleStepException e ) {
      return new RowMeta();
    }
    return rowMeta;
  }
}
