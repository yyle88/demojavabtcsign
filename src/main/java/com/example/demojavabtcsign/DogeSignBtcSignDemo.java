package com.example.demojavabtcsign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.*;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

//当你需要签名BTC主网时请使用：
//import org.bitcoinj.params.MainNetParams;
//当你需要签名BTC测试网时请使用：
//import org.bitcoinj.params.TestNet3Params;
//当你需要签名BTC测试网时请使用：
//import org.bitcoinj.params.RegTestParams;


public class DogeSignBtcSignDemo {

    public static void main(String[] args) {
        {
            byte[] privateKeyByte = Hex.decode("在这里放你的私钥");
            //比如
            //privateKeyByte = Hex.decode("e9b36b68fb6de7f45cb3335bec08da3ba537957b9e56a9e2d9089713619ac7f5");
            ECKey ecKey = ECKey.fromPrivate(privateKeyByte);
            NetworkParameters parameters = DogecoinTestNet3Params.get();

            //第一步你需要放你的私钥，看看算出来的地址是不是你的钱包地址，只有地址完全相同时才是对的
            //假如不是就说明要么是网络参数不匹配，要么是地址类型不同，你就需要确定你的网络和地址类型
            Address address = Address.fromKey(parameters, ecKey, Script.ScriptType.P2PKH);
            System.out.println("address:" + address.toString());
        }

        {
            UnsignedMsg param = new UnsignedMsg();
            //这里是网络参数，当你需要签名DOGE测试网时，就用这个网络
            param.networkParameters = DogecoinTestNet3Params.get();
            //这里是网络参数，当你需要签名BTC主网时，就用这个网络
            //param.networkParameters = MainNetParams.get();
            param.privateKey = "在这里放你的私钥Hex字符串";
            //比如
            //param.privateKey = "e9b36b68fb6de7f45cb3335bec08da3ba537957b9e56a9e2d9089713619ac7f5";
            param.rawTransaction = "在这里放你的初始交易内容";
            //比如
            //param.rawTransaction = "7b22496e7075744c697374223a5b7b2270726576696f75734f7574506f696e74223a7b2248617368223a2237346164656234323162373233323136643331363463366562363239613561323963306338366336656232633431363831633930373761376130393463666133222c22496e646578223a317d2c22706b536372697074223a2264716b5567696a51727969596c4e515a33637232326d65646a7038504667474972413d3d222c22616d6f756e74223a3133373737333539327d5d2c224f75747075744c697374223a5b7b2261646472657373223a226e593738614e796e5451394b6d5950347a587474755976575a455231744d444e4362222c22616d6f756e74223a353839373036397d2c7b2261646472657373223a226e6734503136616e584e5572517736564b486d6f4d57384e4873546b4642644e726e222c22616d6f756e74223a3133313833313132337d5d7d";

            String signedHex = signBtcTxGetHex(param);

            String exceptedResult = "在这里放期望的签名结果";
            if (Objects.equals(signedHex, exceptedResult)) {
                System.out.println("OK");
            } else {
                System.out.println("WA");
            }
        }
    }

    @Data
    public static class UnsignedMsg {
        NetworkParameters networkParameters; //网络配置
        String privateKey;
        String rawTransaction;
    }


    // 主类，表示一个未签名的交易
    @Data
    public static class UnsignedTx {
        @JsonProperty("InputList")
        private List<Input> inputList;

        @JsonProperty("OutputList")
        private List<Output> outputList;

        // 表示一个输入
        @Data
        static class Input {
            @JsonProperty("previousOutPoint")
            private PreviousOutPoint previousOutPoint;

            @JsonProperty("pkScript")
            private byte[] pkScript;

            @JsonProperty("amount")
            private long amount;

            // 表示前一个输出点
            @Data
            static class PreviousOutPoint {
                @JsonProperty("Hash")
                private String hash;

                @JsonProperty("Index")
                private int index;
            }
        }

        // 表示输出信息
        @Data
        static class Output {
            @JsonProperty("address")
            private String address;

            @JsonProperty("amount")
            private long amount;
        }
    }

    public static String signBtcTxGetHex(UnsignedMsg param) {
        byte[] privateKeyByte = Hex.decode(param.getPrivateKey());
        // 把私钥放到私钥盒子里
        ECKey ecKey = ECKey.fromPrivate(privateKeyByte);

        NetworkParameters networkParameters = param.getNetworkParameters();
        // 三种网络 mainnet testnet regtest
        Transaction tx = new Transaction(networkParameters);

        //ECKey ecKey = new ECKey();

        // 第一步：Hex 解码
        byte[] rawTxBytes = Hex.decode(param.getRawTransaction());

        String result = new String(rawTxBytes, StandardCharsets.UTF_8);
        System.out.println(result); // 打印 "Hello"

        // 第二步：JSON 转换
        UnsignedTx unsignedTx = null;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            unsignedTx = objectMapper.readValue(rawTxBytes, UnsignedTx.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (UnsignedTx.Input input : unsignedTx.getInputList()) {
            // 添加input
            TransactionOutPoint transactionOutPoint = new TransactionOutPoint(networkParameters, input.getPreviousOutPoint().getIndex(), Sha256Hash.wrap(input.getPreviousOutPoint().getHash()));
            TransactionInput transactionInput = new TransactionInput(networkParameters, tx, new byte[0], transactionOutPoint, Coin.valueOf(input.getAmount()));
            // 设置RBF，这里设置的是固定值，也是网上通用的做法，当然你也可以设置别的，但不推荐不设置
            tx.addInput(transactionInput).setSequenceNumber(TransactionInput.NO_SEQUENCE - 2);
        }


        for (UnsignedTx.Output output : unsignedTx.getOutputList()) {
            // 添加output
            tx.addOutput(Coin.valueOf(output.getAmount()), Address.fromString(networkParameters, output.getAddress()));
        }

        // 对input进行签名
        for (int i = 0; i < tx.getInputs().size(); i++) {
            TransactionInput input = tx.getInput(i);
            UnsignedTx.Input unSignedTxInput = unsignedTx.inputList.get(i);
            Script scriptPubKey = new Script(unSignedTxInput.getPkScript());


            TransactionSignature signature;
            if (ScriptPattern.isP2PK(scriptPubKey)) {
                signature = tx.calculateSignature(i, ecKey, scriptPubKey, Transaction.SigHash.ALL, false);
                input.setScriptSig(ScriptBuilder.createInputScript(signature));
                input.setWitness((TransactionWitness) null);
            } else if (ScriptPattern.isP2PKH(scriptPubKey)) {
                signature = tx.calculateSignature(i, ecKey, scriptPubKey, Transaction.SigHash.ALL, false);
                input.setScriptSig(ScriptBuilder.createInputScript(signature, ecKey));
                input.setWitness((TransactionWitness) null);
            } else if (ScriptPattern.isP2WPKH(scriptPubKey)) {

                Script scriptCode = ScriptBuilder.createP2PKHOutputScript(ecKey);
                signature = tx.calculateWitnessSignature(i, ecKey, scriptCode, input.getValue(), Transaction.SigHash.ALL, false);
                input.setScriptSig(ScriptBuilder.createEmpty());
                input.setWitness(TransactionWitness.redeemP2WPKH(signature, ecKey));
            } else {
                if (!ScriptPattern.isP2TR(scriptPubKey)) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Don't know how to sign for this kind of scriptPubKey: " + scriptPubKey);
                }
            }
        }
        String signedHex = tx.toHexString();
        System.out.println(signedHex);

        return signedHex; //这个结果就是可以往链上发交易的结果，使用各种语言比如Go/Java最终得到的结果都是相同的（而且要一模一样）
    }
}
