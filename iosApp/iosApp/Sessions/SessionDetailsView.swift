import SwiftUI
import SwiftUIFlowLayout
import ConfettiKit


// This version is using Compose for iOS....
struct SessionDetailsViewShared: UIViewControllerRepresentable {
    var session: SessionDetails
    @Environment(\.openURL) var openURL
    
    func makeUIViewController(context: Context) -> UIViewController {
        return SharedViewControllersKt.SessionDetailsViewController(session: session, socialLinkClicked: { urlString in
            print(urlString)
            if let url = URL(string: urlString) {
                openURL(url)
            }
        })
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}



// This version is using Compose for iOS....
struct SessionSpeakerInfoViewShared: UIViewControllerRepresentable {
    var speaker: SpeakerDetails
    @Environment(\.openURL) var openURL
    @Binding var measuredHeight: CGFloat

    func makeUIViewController(context: Context) -> UIViewController {
        let content = SharedViewControllersKt.SessionSpeakerInfoViewController(speaker: speaker,
            onSocialLinkClicked: { urlString in
                if let url = URL(string: urlString) {
                    openURL(url)
                }
            }, heightChanged: { height in
                let scale = UIScreen.main.scale
                measuredHeight = CGFloat(height)/scale
                print(measuredHeight)
            })
        
        return content
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}




// ...and this one is pure SwiftUI
struct SessionDetailsView: View {
    var session: SessionDetails
    
    @State var measuredHeights: [SessionDetails.Speaker: CGFloat] = [:]

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 6) {
                Text(session.title).font(.system(size: 24)).foregroundColor(.blue)
                
                Text(session.sessionDescription ?? "").font(.system(size: 16))
                                
                if session.tags.count > 0 {
                    FlowLayout(mode: .scrollable,
                               items: session.tags,
                               itemSpacing: 4) {
                        Text($0)
                            .padding(.vertical, 10)
                            .padding(.horizontal)
                            .background(.blue)
                            .foregroundColor(.white)
                            .background(Capsule().stroke())
                            .clipShape(Capsule())
                            .font(.system(size: 16))
                    }
                }
                
                Spacer().frame(height: 32)
                
                ForEach(session.speakers, id: \.self) { speaker in
                    SessionSpeakerInfoViewShared(speaker: speaker.speakerDetails, measuredHeight: binding(for: speaker))
                        .frame(height: binding(for: speaker).wrappedValue)
                }
                Spacer()
            }
            .padding()
        }

    }
    
    private func binding(for key: SessionDetails.Speaker) -> Binding<CGFloat> {
        return .init(
            get: { self.measuredHeights[key, default: 1000] },
            set: { self.measuredHeights[key] = $0 })
    }

}

