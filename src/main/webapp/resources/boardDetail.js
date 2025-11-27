console.log("boardDetail.js.in");
console.log("vscode");
console.log(bnoValue);

document.getElementById("cmtAddBtn").addEventListener("click", () => {
  const cmtWriter = document.getElementById("cmtWriter");
  const cmtText = document.getElementById("cmtText");
  if (cmtText.value == null || cmtText.value == "") {
    alert("댓글 내용을 입력해주세요.");
    return;
  }

  // 댓글 객체 생성
  let cmtData = {
    bno: bnoValue,
    writer: cmtWriter.value,
    content: cmtText.value,
  };

  console.log(cmtData);
  cmtText.value = "";
  cmtText.focus();

  // cmtData를 비동기로 컨트롤러로 보내기
  postCommentToServer(cmtData).then((result) => {
    console.log(result);
    if (result == "1") {
      alert("댓글 등록 성공");
    } else {
      alert("댓글 등록 실패");
    }
    // 댓글 리스트 띄우기
    printCommentList(cmtData.bno);
  });
});

// 데이터 전송 post
async function postCommentToServer(cmtData) {
  try {
    // 보낼때 => url, headers(contentType), body(cmtData)
    const url = "/cmt/post";
    const config = {
      method: "post",
      headers: {
        "Content-Type": "application/json; charset=urf-8",
      },
      body: JSON.stringify(cmtData),
    };
    const resp = await fetch(url, config);
    const result = await resp.text(); // isOk
    return result;
  } catch (error) {
    console.log(error);
  }
}

// 비동기 리스트 호출
async function getCommentListFromServer(bno) {
  try {
    const resp = await fetch(`/cmt/list?bno=${bno}`);
    const result = await resp.json(); // 댓글 리스트 [{...},{...}]
    return result;
  } catch (error) {
    console.log(error);
  }
}

// 댓글 리스트 출력 함수
function printCommentList(bno) {
  // 리스트 가져오는 비동기 함수 호출
  getCommentListFromServer(bno).then((result) => {
    console.log(result);
    const div = document.getElementById("commentLine");
    let str = "";
    if (result.length > 0) {
      // 댓글이 있는 경우
      for (let cmt of result) {
        str += `<div>`;
        str += `<div>${cmt.cno} / ${cmt.writer} (${cmt.regdate})</div>`;
        str += `<div>`;
        str += `<input type="text" value="${cmt.content}">`;
        str += `<button type="button">mod</button>`;
        str += `<button type="button">del</button>`;
        str += `</div></div>`;
      }
      div.innerHTML = str;
    } else {
      // 댓글이 없는 경우
      div.innerHTML = `<div>댓글이 없습니다.</div>`;
    }
  });
}

/*
		<div>
			<div>cno / writer(regdate)</div>
			<div>
				<input type="text" value="content">
				<button type="button">mod</button>
				<button type="button">del</button>
			</div>
		</div>
*/
