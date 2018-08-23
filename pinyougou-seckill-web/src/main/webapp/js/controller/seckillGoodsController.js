/** 定义秒杀商品控制器 */
app.controller("seckillGoodsController", function($scope,$controller,$location,baseService){

    /** 指定继承cartController */
    $controller("baseController", {$scope:$scope});

    $scope.findSeckillGoods=function () {
        baseService.sendGet("/seckill/findSeckillGoods")
            .then(function (response) {
                $scope.seckillGoodsList=response.data;
            })
    }
    $scope.findOne=function () {
        var id = $location.search().id;
        baseService.sendGet("/seckill/findOne?id="+id)
            .then(function (response) {
                $scope.entity=response.data;
                $scope.downcount($scope.entity.endTime);
            })
    }

    $scope.downcount=function (endTime) {

        var milliSeconds = new Date(endTime).getTime()- new Date().getTime();
        var second = Math.floor(milliSeconds/1000);
        if (second>0){
            var minutes = Math.floor(second/60);
            var hours = Math.floor(minutes/60);
            var days = Math.floor(hours/24);
            var array = new Array();
            if(days>0){
                array.push(days+"天");
            }
            if(hours>0){
                array.push((hours-days*24)+":");
            }
            if(minutes>0){
                array.push(minutes-hours*60)+":";
            }
            if(second>0){
                array.push(second-minutes*60);
            }
            $scope.timeStr=array.join("");
            $timeout(function () {
                $scope.downcount(endTime);
            },1000);
        }else {
            $scope.timeStr="秒杀结束,wss";
        }


    }
});