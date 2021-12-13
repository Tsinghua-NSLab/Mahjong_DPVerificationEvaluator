# What is Mahjong?
Mahjong: A Generic Framework for Network Data Plane Verification

The pdf link(after publishment) 

# How to Use?
Import the whole dictionary into Eclipse as a project.

Run the driver/driver.java, and you will get a verification result of the reachability between two leaf nodes in a 2-4-4 fattree network.

Modify the config/TypeConfig.java and try other modules.

# How to Add New Module?
1. Write your module

When adding header expression, read hassel.bean.HS for reference.

When adding preprocessing function, read bean.basis.basicTF.ruleDecouple for reference.

When adding tranfer function, if the transfer function works by step, read hassel.bean.HSATransFunc for reference. If the transfer function works in one breath, read smt.bean.SMTTransFunc for reference.

2. Config it in Factory

Factories are under src/factory. Configs to HeaderFactory, RuleFactory and TransferFuncFactory are needed. Config in AbstractIP when reuse hassel.bean.HS and modify its basic data structure. Config ParserFactory when add new device config parser.

3. Modify src/config.TypeConfig and run!