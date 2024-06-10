const getLocation = document.getElementById('getLocation');

getLocation.addEventListener('click', function (e) {
    e.preventDefault();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, errorPosition);
    } else {
        alert('지오로케이션을 지원하지 않습니다.');
    }
});

function showPosition(position) {
    document.querySelector("#lat").value = position.coords.latitude
    document.querySelector("#lnt").value = position.coords.longitude
}

function errorPosition(err) {
    alert(err.message);
}