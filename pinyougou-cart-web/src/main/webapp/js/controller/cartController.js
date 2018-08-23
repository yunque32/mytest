app.controller('cartController',function ($scope,$controller,baseService) {
    $controller('baseController',{$scope:$scope});
    $scope.findCart=function () {
        baseService.sendGet("/cart/findCart")
            .then(function (response) {
                $scope.carts=response.data;

            })
    }

    $scope.addCart=function (itemId,num) {
        baseService.sendGet("/cart/addCart","itemId="+itemId+"&num="+num)
            .then(function (response) {
                if (response.data){
                    $scope.findCart();
                }else{
                    alert("操作失败");
                }
            })
    }

});