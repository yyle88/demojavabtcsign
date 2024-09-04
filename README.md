# 使用Java给BTC签名的DEMO

### 简单说明
BTC签名逻辑，建议自己写，毕竟涉及到私钥，因此本项目仅供参考和拷贝。

主要逻辑在这里：
[主要逻辑](src/main/java/com/example/demojavabtcsign/DogeSignBtcSignDemo.java)

当然由于我在开发时顺带也接了狗狗币dogecoin，因此这里也同样可以适用于狗狗币的签名（跟BTC签名共用逻辑，区别仅仅在于，链的网络参数不同）。

### 其它说明
当你需要 golang签名btc 或者 golang签名doge 时候，请参考 [这里](https://github.com/yyle88/gobtcsign)

### 特别注意
你需要确保你用的是 dogecoin 而不是 dogechain 或者其他狗链，因为同样是小狗头像图标的链有很多很多种。

### 其它说明
只是技术分享的，而不涉及别的，请理性对待。
