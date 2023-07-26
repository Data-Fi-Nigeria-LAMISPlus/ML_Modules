package org.lamisplus.modules.ml.controller.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MlRequestDTO  implements Serializable {
    @NonNull
    private ModelConfigs modelConfigs;
    @NonNull
    private VariableValues variableValues;
}
