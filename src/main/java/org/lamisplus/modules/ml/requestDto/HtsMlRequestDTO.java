package org.lamisplus.modules.ml.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class HtsMlRequestDTO implements Serializable {
    @NonNull
    private ModelConfigs modelConfigs;
    @NonNull
    private VariableValues variableValues;
}
