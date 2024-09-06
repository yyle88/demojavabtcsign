package com.example.demojavabtcsign;

import org.bitcoinj.core.Address;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

public class ChainAddressCheckDemo {

    //这里简单写个地址校验的逻辑
    //当然通过成熟第三方检查也行，把地址丢在这里 https://sochain.com/ 它就会自动跳转到对应的链对应的网络找到对应的内容
    public static void main(String[] args) {

        {
            String dogeMainNetAddress = "DNTDUFge2sGu54k5yRZhtxPV2n4pdFVdR1";
            Address address = Address.fromString(DogecoinMainNetParams.get(), dogeMainNetAddress);
            System.out.println("DOGE mainnet:" + address.toString());
            System.out.println(address.getOutputScriptType());
        }

        {
            String dogeTestNetAddress = "nhRncWG1Exmnn6nSLxtjEbSoxLJa747j3C";
            Address address = Address.fromString(DogecoinTestNet3Params.get(), dogeTestNetAddress);
            System.out.println("DOGE testnet:" + address.toString());
            System.out.println(address.getOutputScriptType());
        }

        {
            String dogeMainNetAddress = "DJ4bAxxPvoiZxTJD7KZs6jSGStPfRrzEJW";
            //这里拿个主网的地址去匹配测试网肯定会报错
            try {
                Address address = Address.fromString(DogecoinTestNet3Params.get(), dogeMainNetAddress);
                System.out.println("DOGE testnet:" + address.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage()); //这里会报错
            }
        }

        {
            String dogeTestNetAddress = "nqNjvWut21qMKyZb4EPWBEUuVDSHuypVUa";
            //这里拿个测试网的地址去匹配主网肯定会报错
            try {
                Address address = Address.fromString(DogecoinMainNetParams.get(), dogeTestNetAddress);
                System.out.println("DOGE mainnet:" + address.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());//这里会报错
            }
        }

        {
            String btcMainNetAddress = "17xKNFapag74BgPRxdP7qcZE4j4nWyLFxT";
            Address address = Address.fromString(MainNetParams.get(), btcMainNetAddress);
            System.out.println("BTC mainnet:" + address.toString());
            System.out.println(address.getOutputScriptType());
        }

        {
            String btcTestNetAddress = "mnXkBrKfxK3L9GKx6xaKDQXuxuBpinBR8v";
            Address address = Address.fromString(TestNet3Params.get(), btcTestNetAddress);
            System.out.println("BTC testnet:" + address.toString());
            System.out.println(address.getOutputScriptType());
        }

        {
            String btcMainNetAddress = "1Kj1EydTHkhvkKPLz894QgH6PQS88Fp7iz";
            //这里拿个BTC地址去匹配DOGE网络肯定会报错
            try {
                Address address = Address.fromString(DogecoinMainNetParams.get(), btcMainNetAddress);
                System.out.println("BTC mainnet:" + address.toString());
                System.out.println(address.getOutputScriptType());
            } catch (Exception e) {
                System.out.println(e.getMessage()); //这里会报错
            }
        }

        {
            String btcTestNetAddress = "myxFoPaLAT2k3zKdVfAegaF35VVMo5pWbx";
            //这里拿个BTC地址去匹配DOGE网络肯定会报错
            try {
                Address address = Address.fromString(DogecoinTestNet3Params.get(), btcTestNetAddress);
                System.out.println("BTC testnet:" + address.toString());
                System.out.println(address.getOutputScriptType());
            } catch (Exception e) {
                System.out.println(e.getMessage()); //这里会报错
            }
        }
    }
}
