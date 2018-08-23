app.controller("orderController",function ($scope,$controller,baseService) {
    $controller("cartController",{$scope:$scope});
    $scope.findUserAddress=function () {
        baseService.sendGet("/order/findAddressByUser")
            .then(function (response) {
                $scope.addressList=response.data;
                for(var i in response.data){
                    if(response.data[i].isDefault=='1'){
                        $scope.address=response.data[i];
                    return;
                    }
                }

            });
    }

    $scope.selectAddress=function (item) {
        $scope.address=item;
    }

    $scope.isSelectedAddress=function (item) {
        return item=$scope.address;
    }

    $scope.order={paymentType:'1'};
    $scope.selectPayType=function (type) {
        $scope.order.paymentType=type;
    }
    $scope.genPayCode=function () {
        baseService.sendGet("/order/genPayCode")
            .then(function (response) {
            $scope.money=(response.data.totalFee/100).toFixed(2);
            $scope.outTradeNo=response.data.outTradeNo;
            var qr=new QRious({
                elements : document.getElementById('qrious'),
                size:250,
                level:'H',
                value:response.data.codeUrl
            });
        })
    }
});