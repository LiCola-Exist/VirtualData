package virtual;

import java.util.LinkedHashMap;
import virtual.RandomRule.RandomInterface;

/**
 * Created by LiCola on 2018/5/4.
 */
public interface VirtualDataBuilder {

  LinkedHashMap<String, RandomInterface<Boolean>> getRandomRuleBoolean();

  LinkedHashMap<String, RandomInterface<Integer>> getRandomRuleInteger();

  LinkedHashMap<String, RandomInterface<Long>> getRandomRuleLong();

  LinkedHashMap<String, RandomInterface<Float>> getRandomRuleFloat();

  LinkedHashMap<String, RandomInterface<Double>> getRandomRuleDouble();

  LinkedHashMap<String, RandomInterface<String>> getRandomRuleString();

  LinkedHashMap<String, RandomInterface<Object>> getRandomRuleModel();

}
