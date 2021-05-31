package com.lubycon.eatitall.googlesheet;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetResponse {
  private List<String[]> values;
}
