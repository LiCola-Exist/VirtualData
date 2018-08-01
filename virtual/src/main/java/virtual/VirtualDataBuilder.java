package virtual;

import java.util.Map;

/**
 * Created by LiCola on 2018/5/4.
 * Builder接口，定义数据规则部件的结构
 */
public interface VirtualDataBuilder {

  boolean throwConstructorException();

  Map<String, VirtualApi<Boolean>> injectRuleBoolean(Map<String, VirtualApi<Boolean>> map);

  Map<String, VirtualApi<Integer>> injectRuleInteger(Map<String, VirtualApi<Integer>> map);

  Map<String, VirtualApi<Long>> injectRuleLong(Map<String, VirtualApi<Long>> map);

  Map<String, VirtualApi<Float>> injectRuleFloat(Map<String, VirtualApi<Float>> map);

  Map<String, VirtualApi<Double>> injectRuleDouble(Map<String, VirtualApi<Double>> map);

  Map<String, VirtualApi<String>> injectRuleString(Map<String, VirtualApi<String>> map);

  Map<String, VirtualApi<Object>> injectRuleModel(Map<String, VirtualApi<Object>> map);

}
