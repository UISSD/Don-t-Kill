# Don-t-Kill

适用于Android 12的后台保活模块（Xposed模块，建议使用LSPosed管理器安装）



1.本模块理论上适用于绝大部分原生Android 12/13系统和基于Android 12/13的MIUI系统，其他基于Android 12/13的定制ROM可能会因为其系统自带的省电机制影响本模块的保活能力，也有可能会因为其对内核的修改而导致本模块失效。

2.本模块并不是完全禁止了系统的杀后台行为（那还用说！完全禁止的话那内存爆了不就GG了！），所以系统依旧会因为内存吃紧、用户手动清理后台、程序运行崩溃等事件而发生杀后台行为。但很多时候，系统对于“内存吃紧”的定义都过于宽松，所以你有时候会遇到内存还有一半却被系统以“内存吃紧”理由Kill掉后台的惨烈事故，所以在此强烈建议搭配嘟嘟斯基的“Scene的附加模块（二）”一齐使用。

3.在使用Miller的朋友，请更新到Miller v1.1.1及以上，旧版本的Miller与本模块有部分功能重复，一齐使用的话会有冲突风险。

4.本模块修改了Android系统的底层行为，对Android系统来说这显然是一种“不合理”的行为，本质上这也违背了Android后台管理策略，所以本模块并不能保证系统运行的稳定性。

5.请做好救砖准备。



补充：release版无日志，debug版日志路径为/cache/uissd/dontkill与/storage/emulated/0/Android/data/com.miui.powerkeeper/cache/dontkill
