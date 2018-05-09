package virtual;

import java.util.Map;

/**
 * Created by LiCola on 2018/5/4.
 * Builder接口，定义数据规则部件的结构
 */
public interface VirtualDataBuilder {

  Map<String, RandomInterface<Boolean>> injectRuleBoolean(Map<String, RandomInterface<Boolean>> map);

  Map<String, RandomInterface<Integer>> injectRuleInteger(Map<String, RandomInterface<Integer>> map);

  Map<String, RandomInterface<Long>> injectRuleLong(Map<String, RandomInterface<Long>> map);

  Map<String, RandomInterface<Float>> injectRuleFloat(Map<String, RandomInterface<Float>> map);

  Map<String, RandomInterface<Double>> injectRuleDouble(Map<String, RandomInterface<Double>> map);

  Map<String, RandomInterface<String>> injectRuleString(Map<String, RandomInterface<String>> map);

  Map<String, RandomInterface<Object>> injectRuleModel(Map<String, RandomInterface<Object>> map);

}
